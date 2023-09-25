package se.umu.svke0008.havencompanion.domain.utils

import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

class ConflictHelperImpl : ConflictHelper {

    private inline fun <T, K> checkConflict(
        gameCharacters: List<T>,
        groupBy: (T) -> K,
        filter: (List<T>) -> Boolean = { true }
    ): List<T> {
        val conflictingGroups = gameCharacters.groupBy(groupBy)
            .filter { (_, group) -> group.size > 1 }
            .values
            .filter(filter)

        return conflictingGroups.firstOrNull() ?: emptyList()
    }

    override fun checkMonsterConflict(gameCharacters: List<GameCharacter.Monster>): List<GameCharacter.Monster> {
        return checkConflict(gameCharacters, { Pair(it.firstInitiative, it.onConflictOrder) })
    }

    override fun checkFirstInitiativeConflict(gameCharacters: List<GameCharacter.Hero>): List<GameCharacter.Hero> {
        return checkConflict(gameCharacters, { it.firstInitiative }) { group ->
            group.any { it.secondInitiative == null }
        }
    }

    override fun checkHeroConflict(gameCharacters: List<GameCharacter.Hero>): List<GameCharacter.Hero> {
        return checkConflict(gameCharacters, { Pair(it.firstInitiative, it.secondInitiative) }) { group ->
            group.any { it.onConflictOrder == null }
        }
    }

    override fun checkLongRestConflict(gameCharacters: List<GameCharacter.Hero>): List<GameCharacter.Hero> {
        return checkConflict(gameCharacters, { it.characterState == CharacterState.HeroState.LongResting }) { group ->
            group.any { it.onConflictOrder == null }
        }
    }


}