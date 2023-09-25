package se.umu.svke0008.havencompanion.domain.utils

import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.isLongResting
import se.umu.svke0008.havencompanion.domain.model.game_character.isPending
import se.umu.svke0008.havencompanion.domain.model.scenario.InitiativeState

/**
 * The `InitiativeHelperImpl` object provides an implementation of the [InitiativeHelper] interface.
 *
 * This object provides utility functions to sort game characters based on their initiative and to determine the current initiative state.
 * It uses various criteria to sort and determine the initiative state, such as long-resting status, first initiative, second initiative, and conflict order.
 */
object InitiativeHelperImpl : InitiativeHelper {

    /**
     * Sorts the list of game characters based on various criteria.
     *
     * The sorting criteria are as follows:
     * 1. Long-resting status: Characters who are long-resting are placed last.
     * 2. First initiative: Characters are sorted by their first initiative.
     * 3. Hero vs Monster: In case of a tie between a hero and a monster, the hero acts first.
     * 4. Second initiative: If there's a tie between heroes, they are sorted by their second initiative.
     * 5. Conflict order: If there's still a tie, characters are sorted by their conflict order.
     *
     * @param gameCharacters List of [GameCharacter] objects to be sorted.
     * @return A sorted list of [GameCharacter] objects based on the criteria mentioned.
     */
    override fun sort(gameCharacters: List<GameCharacter>): List<GameCharacter> {
        return gameCharacters.sortedWith(
            compareBy(

                { if (it is GameCharacter.Hero && it.isLongResting()) 1 else 0 },

                { it.firstInitiative },

                { if (it is GameCharacter.Monster) 1 else 0 },

                { if (it is GameCharacter.Hero) it.secondInitiative else null },

                { it.onConflictOrder }
            )
        )
    }

    /**
     * Checks for conflicts among monsters based on their first initiative and onConflictOrder.
     *
     * This function groups monsters by their first initiative and onConflictOrder. If there are multiple monsters with the same first initiative and onConflictOrder,
     * they are considered to be in conflict and are added to the conflicting groups.
     *
     * @param gameCharacters List of [GameCharacter.Monster] objects to check for conflicts.
     * @return A list of lists of [GameCharacter.Monster] objects that are in conflict.
     */
    private fun checkMonsterConflict(
        gameCharacters: List<GameCharacter.Monster>
    ): List<List<GameCharacter.Monster>> {
        val sortedMonsters = sort(gameCharacters).filterIsInstance<GameCharacter.Monster>()
        val conflictingGroups = mutableListOf<List<GameCharacter.Monster>>()

        // Group monsters by their first initiative and onConflictOrder
        val groupedByFirstInitiativeAndOnConflictOrder =
            sortedMonsters.groupBy { Pair(it.firstInitiative, it.onConflictOrder) }

        for ((_, group) in groupedByFirstInitiativeAndOnConflictOrder) {
            if (group.size > 1) {
                conflictingGroups.add(group)
            }
        }

        return conflictingGroups
    }

    /**
     * Checks for conflicts among heroes based on their first initiative.
     *
     * This function groups heroes by their first initiative. If there are multiple heroes with the same first initiative and one of them has a null second initiative,
     * that hero is considered to be in conflict.
     *
     * @param gameCharacters List of [GameCharacter.Hero] objects to check for conflicts.
     * @return A [GameCharacter.Hero] object that is in conflict, or null if no conflicts are found.
     */
    private fun checkFirstInitiativeConflict(gameCharacters: List<GameCharacter.Hero>): GameCharacter.Hero? {
        val sortedHeroes = sort(gameCharacters).filterIsInstance<GameCharacter.Hero>()
        val conflictingGroups = mutableListOf<List<GameCharacter.Hero>>()

        val groupedByInitiatives = sortedHeroes.groupBy { it.firstInitiative }

        for ((_, group) in groupedByInitiatives) {
            if (group.size > 1) {
                conflictingGroups.add(group)
            }
        }


        val filteredGroup = conflictingGroups.firstOrNull { group ->
            group.any { it.secondInitiative == null }
        }

        return filteredGroup?.firstOrNull { it.secondInitiative == null }
    }


    /**
     * Checks for conflicts among heroes based on their first and second initiatives.
     *
     * This function groups heroes based on their first and second initiatives. If there are multiple heroes with the same first and second initiatives and one of them has
     * a null onConflictOrder, they are considered to be in conflict.
     *
     * @param gameCharacters List of [GameCharacter.Hero] objects to check for conflicts.
     * @return A list of [GameCharacter.Hero] objects that are in conflict, or null if no conflicts are found.
     */
    private fun checkHeroConflict(
        gameCharacters: List<GameCharacter.Hero>
    ): List<GameCharacter.Hero>? {
        val sortedHeroes = sort(gameCharacters).filterIsInstance<GameCharacter.Hero>()
        val conflictingGroups = mutableListOf<List<GameCharacter.Hero>>()

        // Group heroes based on their first initiative and second initiative
        val groupedByInitiatives = sortedHeroes.groupBy {
            Pair(
                it.firstInitiative,
                it.secondInitiative,
            )
        }

        for ((_, group) in groupedByInitiatives) {
            if (group.size > 1) {
                conflictingGroups.add(group)
            }
        }


        val filteredGroup = conflictingGroups.firstOrNull { group ->
            group.any { it.onConflictOrder == null }
        }


        return filteredGroup
    }


    /**
     * Determines the current initiative state of the provided list of game characters.
     *
     * This method assesses the current state of the game characters (e.g., whether they have taken their turn, are waiting for their turn, etc.)
     * and returns the corresponding [InitiativeState].
     *
     * @param gameCharacters List of [GameCharacter] objects whose initiative state needs to be determined.
     * @return The current [InitiativeState] of the provided game characters.
     */
    override fun getInitiativeState(gameCharacters: List<GameCharacter>): InitiativeState {

        val pending = gameCharacters.any { gameCharacter -> gameCharacter.isPending() }
        val secondInitiativePending =
            checkFirstInitiativeConflict(
                gameCharacters.filterIsInstance<GameCharacter.Hero>()
                    .filter { !it.isLongResting() })
        val heroConflict = checkHeroConflict(gameCharacters.filterIsInstance<GameCharacter.Hero>())
        val monsterConflict =
            checkMonsterConflict(gameCharacters.filterIsInstance<GameCharacter.Monster>())

        return when {
            pending -> InitiativeState.Pending(
                gameCharacters.first { it.isPending() /*it.firstInitiative == null && !it.isExhausted()*/ },
                gameCharacters.filter { it.isPending() /* it.firstInitiative == null && !it.isExhausted()*/})

            secondInitiativePending != null ->
                InitiativeState.SetSecondInitiative(secondInitiativePending)

            heroConflict != null -> InitiativeState.HeroConflict(heroConflict)
            monsterConflict.isNotEmpty() -> InitiativeState.MonsterConflict(monsterConflict.first())
            else -> InitiativeState.Done
        }

    }

}
