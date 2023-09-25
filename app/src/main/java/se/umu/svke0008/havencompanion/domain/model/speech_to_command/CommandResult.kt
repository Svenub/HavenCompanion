package se.umu.svke0008.havencompanion.domain.model.speech_to_command

import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

/**
 * Represents the result of a speech-to-command translation.
 *
 * This sealed class encapsulates the different types of commands that can be derived
 * from speech input, such as setting a character's initiative or changing a character's state.
 */
sealed class CommandResult {

    /**
     * Represents a command to set a character's initiative.
     *
     * @property gameCharacter The character for which the initiative is being set.
     * @property initiative The initiative value to be set for the character.
     */
    data class InitiativeCommand(val gameCharacter: GameCharacter, val initiative: Int) :
        CommandResult()

    /**
     * Represents a command to change a character's state.
     *
     * @property gameCharacter The character whose state is being changed.
     * @property characterState The new state to be set for the character.
     */
    data class InitiativeStateCommand(
        val gameCharacter: GameCharacter,
        val characterState: CharacterState
    ) : CommandResult()
}

