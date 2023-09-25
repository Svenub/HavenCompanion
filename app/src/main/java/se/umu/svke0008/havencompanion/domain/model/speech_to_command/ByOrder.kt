package se.umu.svke0008.havencompanion.domain.model.speech_to_command

import android.util.Log
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState.Companion.EXHAUSTED
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState.Companion.LONG_REST
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.utils.LanguageCode
import se.umu.svke0008.havencompanion.domain.utils.WordToNumber
import java.util.Locale

/**
 * A class that translates speech to commands based on character order.
 *
 * This class is responsible for parsing spoken commands and translating them into actions
 * for game characters based on their order in the list. The commands can be to set a character's initiative
 * or change a character's state.
 */
class ByOrder : SpeechToCommand() {

    override fun execute(
        speech: List<String>,
        gameCharacters: List<GameCharacter>,
        threshold: Double
    ): List<CommandResult> {

        if (speech.isEmpty())
            return emptyList()

        val result = serializeSpeech(speech)

        Log.e("execute", result.toString())

        val characterResult = mutableListOf<CommandResult>()

        for (i in result.indices) {
            if (i < gameCharacters.size) {
                val character = gameCharacters[i]
                val command: CommandResult? = when (result[i]) {
                    LONG_REST -> when (character) {
                        is GameCharacter.Hero -> CommandResult.InitiativeStateCommand(
                            character,
                            CharacterState.HeroState.LongResting
                        )
                        is GameCharacter.Monster -> CommandResult.InitiativeStateCommand(
                            character,
                            CharacterState.MonsterState.Pending
                        )
                    }

                    EXHAUSTED -> when (character) {
                        is GameCharacter.Hero -> CommandResult.InitiativeStateCommand(
                            character,
                            CharacterState.HeroState.Exhausted
                        )
                        is GameCharacter.Monster -> CommandResult.InitiativeStateCommand(
                            character,
                            CharacterState.MonsterState.Exhausted
                        )
                    }

                    else -> {
                        result[i].toIntOrNull()?.let {
                            CommandResult.InitiativeCommand(character, it)
                        }
                    }
                }

                command?.let {
                    characterResult.add(it)
                }
            }
        }


        return characterResult
    }

    /**
     * Serializes the speech input and converts it into a list of commands.
     *
     * This method processes the spoken input, identifies key commands like "long rest" and "exhausted",
     * and translates them into a list of commands. It also converts spoken numbers into their numeric form.
     *
     * @param speech The spoken input to be serialized.
     * @param threshold The similarity threshold for matching commands.
     * @return A list of commands derived from the serialized speech input.
     */
    private fun serializeSpeech(speech: List<String>, threshold: Double = 0.8): List<String> {
        val serializeSpeech = speech.joinToString(" ").replace(".", " ")

        // remove unnecessary white spaces and convert to lowercase
        val cleanedSpeech = serializeSpeech.trim().lowercase(Locale.ROOT)


        val words = cleanedSpeech.split(" ")

        val convertedNumbers = mutableListOf<String>()

        var skipNext = false
        for (i in words.indices) {
            if (skipNext) {
                skipNext = false
                continue
            }

            when {
                i < words.size - 1 && isWordLongRest("${words[i]} ${words[i + 1]}", threshold) -> {
                    convertedNumbers.add(LONG_REST)
                    skipNext = true
                }

                isWordLongRest(words[i], threshold) -> convertedNumbers.add(LONG_REST)
                isWordExhausted(words[i], threshold) -> convertedNumbers.add(EXHAUSTED)
                else -> WordToNumber.wordToStringNumber(
                    words[i],
                    LanguageCode.ENG,
                    LanguageCode.SWE
                )?.let {
                    convertedNumbers.add(it)
                } ?: words[i].let {
                    convertedNumbers.add(it)
                }
            }
        }
        return convertedNumbers
    }


}


