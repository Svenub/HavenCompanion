package se.umu.svke0008.havencompanion.domain.utils

import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.scenario.InitiativeState

/**
 * The `InitiativeHelper` interface provides methods to manage and determine the initiative state of game characters.
 *
 * This interface provides utility functions to sort game characters based on their initiative and to determine the current initiative state.
 */
interface InitiativeHelper {

    /**
     * Sorts the list of game characters based on their initiative.
     *
     *
     * @param gameCharacters List of [GameCharacter] objects to be sorted.
     * @return A sorted list of [GameCharacter] objects based on their initiative.
     */
    fun sort(gameCharacters: List<GameCharacter>): List<GameCharacter>

    /**
     * Determines the current initiative state of the provided list of game characters.
     *
     *
     * @param gameCharacters List of [GameCharacter] objects whose initiative state needs to be determined.
     * @return The current [InitiativeState] of the provided game characters.
     */
    fun getInitiativeState(gameCharacters: List<GameCharacter>): InitiativeState

}
