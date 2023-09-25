package se.umu.svke0008.havencompanion.domain.service

import kotlinx.coroutines.flow.Flow
import org.burnoutcrew.reorderable.ItemPosition
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.scenario.InitiativeState
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState

/**
 * An interface that defines the contract for a scenario service.
 *
 * The `ScenarioService` provides methods and properties to manage and observe the state of a game scenario.
 * It offers functionalities to manipulate game characters, their initiatives, states, and other related operations.
 */
interface ScenarioService {

    /**
     * Represents the current state of the scenario.
     */
    val scenarioState: Flow<ScenarioState>

    /**
     * Provides a list of all game characters.
     */
    val allGameCharacters: Flow<List<GameCharacter>>

    /**
     * Provides a list of characters currently in play.
     */
    val charactersInPlay: Flow<List<GameCharacter>>

    /**
     * Provides a list of characters that are exhausted.
     */
    val exhaustedCharacters: Flow<List<GameCharacter>>

    /**
     * Provides a list of hero characters.
     */
    val heroes: Flow<List<GameCharacter>>

    /**
     * Provides a list of monster characters.
     */
    val monsters: Flow<List<GameCharacter>>

    /**
     * Indicates if a new round has started.
     */
    val isNewRound: Flow<Boolean>

    /**
     * Represents the current initiative state.
     */
    val initiativeState: Flow<InitiativeState>

    /**
     * Retrieves and sets the saved state of the scenario.
     *
     * @param scenarioState The state to be retrieved.
     */
    fun retrieveSavedScenarioState(scenarioState: ScenarioState)

    /**
     * Sets the first initiative for a given game character.
     *
     * @param gameCharacter The character whose initiative is to be set.
     * @param firstInitiative The initiative value.
     */
    fun setFirstInitiative(gameCharacter: GameCharacter, firstInitiative: Int)

    /**
     * Sets the second initiative for a given game character.
     *
     * @param gameCharacter The character whose initiative is to be set.
     * @param secondInitiative The initiative value.
     */
    fun setSecondInitiative(gameCharacter: GameCharacter, secondInitiative: Int)

    /**
     * Changes the initiative of a given game character.
     *
     * @param gameCharacter The character whose initiative is to be changed.
     */
    fun initiativeChange(gameCharacter: GameCharacter)

    /**
     * Changes the initiative of a list of game characters.
     *
     * @param gameCharacters The list of characters whose initiatives are to be changed.
     */
    fun initiativeChange(gameCharacters: List<GameCharacter>)

    /**
     * Changes the state of a given game character.
     *
     * @param gameCharacter The character whose state is to be changed.
     * @param characterState The new state.
     */
    fun changeCharacterState(gameCharacter: GameCharacter, characterState: CharacterState)

    /**
     * Adds a game character to the scenario.
     *
     * @param gameCharacter The character to be added.
     */
    fun addGameCharacter(gameCharacter: GameCharacter)

    /**
     * Adds multiple game characters to the scenario.
     *
     * @param characters The list of characters to be added.
     */
    fun addMultipleCharacters(characters: List<GameCharacter>)

    /**
     * Removes a game character from the scenario.
     *
     * @param gameCharacter The character to be removed.
     */
    fun removeGameCharacter(gameCharacter: GameCharacter)

    /**
     * Edits the details of a game character.
     *
     * @param gameCharacter The character to be edited.
     */
    fun editGameCharacter(gameCharacter: GameCharacter)

    /**
     * Moves a game character up in the order.
     *
     * @param gameCharacter The character to be moved up.
     */
    fun moveCharacterUp(gameCharacter: GameCharacter)

    /**
     * Moves a game character down in the order.
     *
     * @param gameCharacter The character to be moved down.
     */
    fun moveCharacterDown(gameCharacter: GameCharacter)

    /**
     * Starts a new round in the scenario.
     */
    suspend fun startNewRound()

    /**
     * Restores the initiative state of the scenario.
     *
     */
    fun restoreInitiativeState()

    /**
     * Toggles the exhausted state of a game character.
     *
     * @param gameCharacter The character whose exhausted state is to be toggled.
     */
    fun toggleExhausted(gameCharacter: GameCharacter)

    /**
     * Clears all characters from the scenario.
     */
    fun clearCharacters()

    /**
     * Moves a game character from one position to another.
     *
     * @param fromPos The starting position.
     * @param toPos The destination position.
     */
    fun moveCharacter(fromPos: ItemPosition, toPos: ItemPosition)

    /**
     * Cancels the current initiative in the scenario.
     */
    fun cancelInitiative()
}
