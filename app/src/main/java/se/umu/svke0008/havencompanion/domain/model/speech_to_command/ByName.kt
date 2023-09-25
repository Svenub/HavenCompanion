package se.umu.svke0008.havencompanion.domain.model.speech_to_command

import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.utils.Utils.levenshteinDistancePercentage
import java.util.Locale


/**
 * A class that translates speech to commands based on character names.
 *
 * This class is responsible for parsing spoken commands and translating them into actions
 * for game characters based on their names. The commands can be to set a character's initiative
 * or change a character's state.
 */
class ByName : SpeechToCommand() {

    override fun execute(
        speech: List<String>,
        gameCharacters: List<GameCharacter>,
        threshold: Double
    ): List<CommandResult> {

        if (speech.isEmpty())
            return emptyList()

        // remove unnecessary white spaces and convert to lowercase
        val cleanedSpeech = speech[0].trim().lowercase(Locale.ROOT)

        // split the text into separate words
        val words = cleanedSpeech.split(" ")

        var characterNameAccumulator = ""
        var initiativeValue: Int?

        val characterResult = mutableListOf<CommandResult>()

        for (i in words.indices) {
            // If the word is a number
            if (words[i].toIntOrNull() != null) {
                initiativeValue = words[i].toInt()

                // Check if characterNameAccumulator is not empty
                if (characterNameAccumulator.isNotBlank()) {
                    val character =
                        getCharacterByThreshold(characterNameAccumulator, gameCharacters, threshold)
                    character?.let {
                        characterResult.add(CommandResult.InitiativeCommand(it, initiativeValue))
                    }
                }

                // Reset accumulator
                characterNameAccumulator = ""
            } else if (isWordLongRest(words[i], threshold)) {

                // Check if characterNameAccumulator is not empty
                if (characterNameAccumulator.isNotBlank()) {
                    val character =
                        getCharacterByThreshold(characterNameAccumulator, gameCharacters, threshold)

                    character?.let {
                        when (character) {
                            is GameCharacter.Hero -> characterResult.add(
                                CommandResult.InitiativeStateCommand(
                                    it,
                                    CharacterState.HeroState.LongResting
                                )
                            )

                            is GameCharacter.Monster -> characterResult.add(
                                CommandResult.InitiativeStateCommand(
                                    it,
                                    CharacterState.MonsterState.Exhausted
                                )
                            )
                        }
                    }
                }

                // Reset accumulator
                characterNameAccumulator = ""
            } else if (isWordExhausted(words[i], threshold)) {

                // Check if characterNameAccumulator is not empty
                if (characterNameAccumulator.isNotBlank()) {
                    val character =
                        getCharacterByThreshold(characterNameAccumulator, gameCharacters, threshold)

                    character?.let {
                        when (character) {
                            is GameCharacter.Hero -> characterResult.add(
                                CommandResult.InitiativeStateCommand(
                                    it,
                                    CharacterState.HeroState.Exhausted
                                )
                            )

                            is GameCharacter.Monster -> characterResult.add(
                                CommandResult.InitiativeStateCommand(
                                    it,
                                    CharacterState.MonsterState.Exhausted
                                )
                            )
                        }
                    }
                }

                // Reset accumulator
                characterNameAccumulator = ""

            } else {
                // If the word is not a number, accumulate it
                characterNameAccumulator += words[i]
            }
        }

        return characterResult
    }

    /**
     * Retrieves a game character based on a similarity threshold.
     *
     * This method tries to find a game character whose name or alias matches the provided name
     * based on a similarity threshold.
     *
     * @param name The name to be matched.
     * @param gameCharacters The list of available game characters.
     * @param threshold The similarity threshold for matching character names.
     * @return The matching game character or null if no match is found.
     */
    private fun getCharacterByThreshold(
        name: String,
        gameCharacters: List<GameCharacter>,
        threshold: Double
    ): GameCharacter? {
        if (threshold !in 0.0..1.0) {
            throw IllegalArgumentException("Threshold must be between 0.0 and 1.0")
        }
        val character =
            gameCharacters.find { it.characterName.lowercase(Locale.ROOT) == name.lowercase(Locale.ROOT) }
                ?: gameCharacters.find {
                    it.nameAlias.any { alias ->
                        alias.lowercase(Locale.ROOT) == name.lowercase(Locale.ROOT)
                    }
                }

        return if (character != null || threshold == 0.00) {
            character
        } else {
            gameCharacters.flatMap { gameCharacter ->
                (listOf(gameCharacter.characterName) + gameCharacter.nameAlias)
                    .map { nameAndAliases ->
                        Pair(
                            gameCharacter, levenshteinDistancePercentage(
                                nameAndAliases.lowercase(Locale.ROOT), name.lowercase(Locale.ROOT)
                            )
                        )
                    }
            }.maxByOrNull { maxPair -> maxPair.second }
                ?.takeIf { satisfiedPair -> satisfiedPair.second >= threshold }?.first
        }
    }

}