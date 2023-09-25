package se.umu.svke0008.havencompanion.domain.model.speech_to_command

import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.utils.Utils

/**
 * Abstract base class for converting speech input into game commands.
 *
 * This class provides the foundation for implementing speech-to-command functionality,
 * allowing subclasses to define the specifics of how speech is translated into game commands.
 */
abstract class SpeechToCommand {

    /**
     * Translates the given speech input into a list of game commands.
     *
     * @param speech List of strings representing the spoken words or phrases.
     * @param gameCharacters List of all characters participating in the scenario.
     * @param threshold A value between 0.0 and 1.0 that determines the similarity threshold for word matching.
     * @return List of [CommandResult] representing the interpreted commands.
     */
    abstract fun execute(
        speech: List<String>,
        gameCharacters: List<GameCharacter>,
        threshold: Double = 0.8
    ): List<CommandResult>

    /**
     * Determines if the given word corresponds to the "Long Rest" command.
     *
     * @param word The word to check.
     * @param threshold A value between 0.0 and 1.0 that determines the similarity threshold for word matching.
     * @return `true` if the word is recognized as "Long Rest", `false` otherwise.
     */
    internal fun isWordLongRest(word: String, threshold: Double): Boolean {
        if (threshold !in 0.0..1.0) {
            throw IllegalArgumentException("Threshold must be between 0.0 and 1.0")
        }

        val primary = Utils.levenshteinDistancePercentage(CharacterState.LONG_REST, word)
        val variation1 = Utils.levenshteinDistancePercentage("long resting", word)
        val variation2 = Utils.levenshteinDistancePercentage("longrest", word)
        return primary >= threshold || variation1 >= threshold || variation2 >= threshold
    }

    /**
     * Determines if the given word corresponds to the "Exhausted" command.
     *
     * @param word The word to check.
     * @param threshold A value between 0.0 and 1.0 that determines the similarity threshold for word matching.
     * @return `true` if the word is recognized as "Exhausted", `false` otherwise.
     */
    internal fun isWordExhausted(word: String, threshold: Double): Boolean {
        if (threshold !in 0.0..1.0) {
            throw IllegalArgumentException("Threshold must be between 0.0 and 1.0")
        }

        val result = Utils.levenshteinDistancePercentage(CharacterState.EXHAUSTED, word)

        return result >= threshold
    }
}
