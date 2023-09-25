package se.umu.svke0008.havencompanion.domain.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.burnoutcrew.reorderable.ItemPosition
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.isExhausted
import se.umu.svke0008.havencompanion.domain.model.game_character.isPending
import se.umu.svke0008.havencompanion.domain.model.scenario.InitiativeState
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState
import se.umu.svke0008.havencompanion.domain.utils.ConflictHelper
import se.umu.svke0008.havencompanion.domain.utils.ConflictHelperImpl
import se.umu.svke0008.havencompanion.domain.utils.InitiativeHelperImpl
import java.time.LocalTime
import java.util.Collections

/**
 * Implementation of the [ScenarioService] interface.
 *
 * This class provides functionalities to manage and observe the state of a game scenario.
 * It offers methods to manipulate game characters, their initiatives, states, and other related operations.
 */
class ScenarioServiceImpl : ScenarioService {


    private val conflictHelper: ConflictHelper = ConflictHelperImpl()

    /**
     * Represents the current mutable state of the scenario.
     */
    private val _scenarioState = MutableStateFlow(ScenarioState.getInstance())

    override val scenarioState: Flow<ScenarioState> = _scenarioState.asStateFlow()

    private var savedCharacters: List<GameCharacter> = emptyList()

    override val allGameCharacters =
        _scenarioState.map { it.allGameCharacters }


    override val charactersInPlay: Flow<List<GameCharacter>> =
        allGameCharacters.combine(scenarioState) { gameCharacters, scenarioState ->
            val nonExhaustedCharacters = gameCharacters.filter { !it.isExhausted() }
            when (scenarioState.initiativeState) {
                is InitiativeState.NewRound -> nonExhaustedCharacters
                else -> InitiativeHelperImpl.sort(nonExhaustedCharacters)
            }
        }

    private val currentPendingIndex = MutableStateFlow(0)

        override val exhaustedCharacters: Flow<List<GameCharacter>> =
        allGameCharacters.map { gameCharacters ->
            gameCharacters.filter { gameCharacter ->
                gameCharacter.isExhausted()
            }
        }


    override val heroes =
        allGameCharacters.map { character -> character.filterIsInstance<GameCharacter.Hero>() }

    override val monsters =
        allGameCharacters.map { character -> character.filterIsInstance<GameCharacter.Monster>() }

    override val isNewRound: Flow<Boolean> =
        scenarioState.map { it.initiativeState is InitiativeState.NewRound }

    val pendingCharacters: Flow<List<GameCharacter>> = allGameCharacters.map { list ->
        list.filter { it.isPending() }
    }

    val conflictingMonsters: Flow<List<GameCharacter.Monster>> =
        allGameCharacters.map { characters ->
            val inPlay = characters.filter { !it.isExhausted() }
            conflictHelper.checkMonsterConflict(inPlay.filterIsInstance<GameCharacter.Monster>())
        }
    val conflictingHeroesFirstInitiative: Flow<List<GameCharacter.Hero>> =
        allGameCharacters.map { characters ->
            val inPlay = characters.filter { !it.isExhausted() }
            conflictHelper.checkFirstInitiativeConflict(inPlay.filterIsInstance<GameCharacter.Hero>())
        }
    val conflictingHeroesSecondInitiative: Flow<List<GameCharacter.Hero>> =
        allGameCharacters.map { characters ->
            val inPlay = characters.filter { !it.isExhausted() }
            conflictHelper.checkHeroConflict(characters.filterIsInstance<GameCharacter.Hero>())
        }

    val conflictingLongRestingHeroes: Flow<List<GameCharacter.Hero>> =
        allGameCharacters.map { characters ->
            val inPlay = characters.filter { !it.isExhausted() }
            conflictHelper.checkLongRestConflict(characters.filterIsInstance<GameCharacter.Hero>())
        }

    val initiativeState2: Flow<InitiativeState> = combine(
        pendingCharacters,
        conflictingMonsters,
        conflictingHeroesFirstInitiative,
        conflictingHeroesSecondInitiative,
        conflictingLongRestingHeroes
    ) {
        pending, monsters, firstConflict, secondConflict, restConflict ->
        when {
            pending.isNotEmpty() -> InitiativeState.Pending(pending.first(), pending)
            monsters.isNotEmpty() -> InitiativeState.MonsterConflict(monsters)
            firstConflict.isNotEmpty() -> InitiativeState.SetSecondInitiative(firstConflict.first())
            secondConflict.isNotEmpty() -> InitiativeState.HeroConflict(secondConflict)
            restConflict.isNotEmpty() -> InitiativeState.LongRestConflict(restConflict)
            else -> InitiativeState.Done
        }
    }




    /**
     * Starts a new round in the game scenario.
     *
     * This function performs the following steps:
     * 1. Retrieves the current scenario state.
     * 2. Saves the characters that are not exhausted to the [savedCharacters] list.
     * 3. Updates the scenario state to reset the initiative values and character states for all characters:
     *    - For heroes, the first and second initiative values, as well as the conflict order, are set to null.
     *      The character state is set to `Pending` if the hero is not exhausted, otherwise, it remains unchanged.
     *    - For monsters, the first initiative value and conflict order are set to null.
     *      The character state is set to `Pending` if the monster is not exhausted, otherwise, it remains unchanged.
     * 4. Updates the scenario state to set the initiative state to `NewRound` and increments the number of rounds done.
     *
     * @return [Unit].
     */
    override suspend fun startNewRound() {
        val scenarioState = _scenarioState.first()
        if(scenarioState.allGameCharacters.isEmpty()) {
            return
        }
        savedCharacters = scenarioState.allGameCharacters.filter { !it.isExhausted() }


        _scenarioState.update {
            it.copy(
                allGameCharacters = it.allGameCharacters.map { gameCharacter ->
                    when (gameCharacter) {
                        is GameCharacter.Hero -> gameCharacter.copy(
                            firstInitiative = null,
                            secondInitiative = null,
                            onConflictOrder = null,
                            characterState = if (gameCharacter.isExhausted())
                                gameCharacter.characterState
                            else CharacterState.HeroState.Pending

                        )

                        is GameCharacter.Monster -> gameCharacter.copy(
                            firstInitiative = null,
                            onConflictOrder = null,
                            characterState = if (gameCharacter.isExhausted())
                                gameCharacter.characterState
                            else CharacterState.MonsterState.Pending
                        )
                    }
                }
            )
        }
        _scenarioState.update {
            it.copy(
                initiativeState = InitiativeState.NewRound,
                roundsDone = it.roundsDone + 1
            )
        }
        _scenarioState.update {
            it.copy(
               date = if(it.roundsDone == 1) LocalTime.now() else it.date
            )
        }
        currentPendingIndex.update { 0 }
    }

    override fun restoreInitiativeState() {
        _scenarioState.update { state -> state.copy(allGameCharacters = savedCharacters) }
    }

    /**
     * Represents the initiative state of the game scenario as an immutable flow.
     *
     * This property observes the current [scenarioState] and determines the initiative state based on the current state of the game.
     * It uses the `flatMapLatest` operator to transform the current state into the corresponding initiative state.
     * The initiative state can be one of the following:
     * - NewRound: Represents the start of a new round in the game.
     * - Cancelled: Indicates that the initiative has been cancelled.
     * - NoEvent: Represents the initial state of the initiative.
     * - Done: Indicates that the initiative process is complete.
     *
     * @return A [Flow] of [InitiativeState] representing the current initiative state of the game.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override val initiativeState: Flow<InitiativeState> = scenarioState.flatMapLatest { state ->
        val initiativeState = state.initiativeState


        when (initiativeState) {
            is InitiativeState.NewRound -> {
                charactersInPlay.flatMapLatest { characters ->
                    val newState = InitiativeHelperImpl.getInitiativeState(characters)
                    flowOf(when (newState) {
                        is InitiativeState.Pending -> InitiativeState.Pending(
                            newState.pendingCharacter,
                            newState.pendingCharacters
                        )

                        is InitiativeState.SetSecondInitiative -> InitiativeState.SetSecondInitiative(
                            newState.gameCharacter
                        )

                        is InitiativeState.HeroConflict -> InitiativeState.HeroConflict(newState.conflictingHeroes)
                        is InitiativeState.MonsterConflict -> InitiativeState.MonsterConflict(
                            newState.conflictingMonsters
                        )

                        is InitiativeState.LongRestConflict -> InitiativeState.LongRestConflict(
                            newState.conflictingHeroes
                        )

                        else -> {
                            _scenarioState.update { scenarioState ->
                                scenarioState.copy(
                                    initiativeState = InitiativeState.Done
                                )
                            }
                            InitiativeState.Done
                        }
                    })
                }
            }

            is InitiativeState.Cancelled -> flowOf(InitiativeState.Cancelled)
            is InitiativeState.Initial -> flowOf(InitiativeState.Initial)
            else -> flowOf(InitiativeState.Done)
        }
    }


    override fun retrieveSavedScenarioState(scenarioState: ScenarioState) {
        _scenarioState.update { scenarioState }
    }


    override fun setFirstInitiative(gameCharacter: GameCharacter, firstInitiative: Int) {
        updateScenarioState {
            val updatedCharacters = allGameCharacters.map { character ->
                when (character) {
                    is GameCharacter.Hero -> {
                        if (character == gameCharacter) {
                            character.copy(
                                firstInitiative = firstInitiative,
                                characterState = CharacterState.HeroState.Normal
                            )
                        } else {
                            character
                        }
                    }

                    is GameCharacter.Monster -> {
                        if (character == gameCharacter) {
                            character.copy(
                                firstInitiative = firstInitiative,
                                characterState = CharacterState.MonsterState.Normal
                            )
                        } else {
                            character
                        }
                    }
                }
            }
            copy(allGameCharacters = updatedCharacters)
        }
    }

    override fun setSecondInitiative(gameCharacter: GameCharacter, secondInitiative: Int) {
        updateScenarioState {
            val updatedCharacters = allGameCharacters.map { character ->
                when (character) {
                    is GameCharacter.Hero -> {
                        if (character == gameCharacter) {
                            character.copy(secondInitiative = secondInitiative)
                        } else {
                            character
                        }
                    }

                    is GameCharacter.Monster -> {
                        character
                    }
                }
            }
            copy(allGameCharacters = updatedCharacters)
        }
    }

    override fun initiativeChange(gameCharacter: GameCharacter) {
        updateScenarioState {
            val updatedCharacters = allGameCharacters.map { character ->
                when (character) {
                    is GameCharacter.Hero -> {
                        if (character.characterName == gameCharacter.characterName) {
                            gameCharacter
                        } else {
                            character
                        }
                    }

                    is GameCharacter.Monster -> {
                        if (character.characterName == gameCharacter.characterName) {
                            gameCharacter
                        } else {
                            character
                        }
                    }
                }
            }
            copy(allGameCharacters = updatedCharacters)
        }

    }


    override fun initiativeChange(gameCharacters: List<GameCharacter>) {
        updateScenarioState {
            val updatedCharacters = allGameCharacters.map { existingCharacter ->
                gameCharacters.find { updatedCharacter ->
                    updatedCharacter.characterName == existingCharacter.characterName
                } ?: existingCharacter
            }
            copy(allGameCharacters = updatedCharacters)
        }
    }

    override fun changeCharacterState(
        gameCharacter: GameCharacter,
        characterState: CharacterState
    ) {
        updateScenarioState {
            val updatedCharacters = allGameCharacters.map { character ->
                when (character) {
                    is GameCharacter.Hero -> {
                        if (character.characterName == gameCharacter.characterName) {
                            character.copy(characterState = characterState as CharacterState.HeroState)
                        } else {
                            character
                        }
                    }

                    is GameCharacter.Monster -> {
                        if (character.characterName == gameCharacter.characterName) {
                            character.copy(characterState = characterState as CharacterState.MonsterState)
                        } else {
                            character
                        }
                    }
                }
            }
            copy(allGameCharacters = updatedCharacters)
        }
    }


    override fun addGameCharacter(gameCharacter: GameCharacter) {
        updateScenarioState {
            copy(allGameCharacters = allGameCharacters + gameCharacter)
        }
    }

    override fun addMultipleCharacters(characters: List<GameCharacter>) {
        updateScenarioState {
            copy(allGameCharacters = allGameCharacters + characters)
        }
    }

    override fun removeGameCharacter(gameCharacter: GameCharacter) {
        updateScenarioState {
            copy(allGameCharacters = allGameCharacters.filter {
                it.characterName != gameCharacter.characterName
            })
        }
    }


    override fun toggleExhausted(gameCharacter: GameCharacter) {
        updateScenarioState {
            copy(allGameCharacters = allGameCharacters.map { character ->
                if (character.characterName == gameCharacter.characterName) {
                    when (character) {
                        is GameCharacter.Hero ->
                            character.copy(characterState = CharacterState.HeroState.Normal)

                        is GameCharacter.Monster ->
                            character.copy(characterState = CharacterState.MonsterState.Normal)
                    }
                } else
                    character
            })
        }
    }

    override fun clearCharacters() {
        updateScenarioState {
            copy(allGameCharacters = emptyList())
        }
    }

    /**
     * Moves a game character from one position to another within the list of all game characters.
     *
     * This function performs the following steps:
     * 1. Retrieves the current order of game characters.
     * 2. Checks if the list of game characters is not empty.
     * 3. Removes the game character from the specified [fromPos] and adds it to the specified [toPos].
     * 4. Updates the scenario state with the new order of game characters.
     *
     * @param fromPos [ItemPosition] representing the current position of the game character to be moved.
     * @param toPos [ItemPosition] representing the target position where the game character should be moved to.
     * @return [Unit].
     */
    override fun moveCharacter(fromPos: ItemPosition, toPos: ItemPosition) {
        updateScenarioState {
            val newOrder = allGameCharacters.map { it }.toMutableList().apply {
                if (this.isNotEmpty())
                    add(toPos.index, removeAt(fromPos.index))
            }
            copy(allGameCharacters = newOrder)

        }
    }

    override fun cancelInitiative() {
        _scenarioState.update {
            it.copy(
                initiativeState = InitiativeState.Cancelled,
                roundsDone = it.roundsDone - 1
            )
        }
    }

    override fun editGameCharacter(gameCharacter: GameCharacter) {
        updateScenarioState {
            copy(allGameCharacters =
            allGameCharacters.map {
                if (it == gameCharacter) gameCharacter else it
            }
            )
        }
    }


    override fun moveCharacterUp(gameCharacter: GameCharacter) {
        updateScenarioState {
            val index = allGameCharacters.indexOf(gameCharacter)
            val newOrder = allGameCharacters.map { it }
            if (index > 0) {
                Collections.swap(newOrder, index, index - 1)
            }
            copy(allGameCharacters = newOrder)
        }
    }

    override fun moveCharacterDown(gameCharacter: GameCharacter) {
        updateScenarioState {
            val index = allGameCharacters.indexOf(gameCharacter)
            val newOrder = allGameCharacters.map { it }
            if (index < newOrder.size - 1) {
                Collections.swap(newOrder, index, index + 1)
            }
            copy(allGameCharacters = newOrder)
        }
    }


    /**
     * Updates the scenario state using a provided helper function.
     *
     * @param block Function that describes how to update the scenario state. This function should be
     * defined in a way that it receives a ScenarioState object and returns a ScenarioState object.
     * @return [Unit].
     */
    private fun updateScenarioState(block: ScenarioState.() -> ScenarioState) {
        _scenarioState.value = block(_scenarioState.value)
    }


}

/**
 * Extension function to check if a list of [GameCharacter] contains a specific game character.
 *
 * @param gameCharacter The game character to check.
 * @return [Boolean] indicating if the list contains the specified game character.
 */
fun List<GameCharacter>.containsCharacter(gameCharacter: GameCharacter): Boolean {
    for (char in this) {
        if (char.characterName == gameCharacter.characterName)
            return true
    }
    return false
}