package se.umu.svke0008.havencompanion.domain.utils

import java.util.Locale

/**
 * Utility object to convert word representations of numbers to their integer or string counterparts.
 *
 * This utility supports multiple languages and can be extended to support more in the future.
 * Currently supported languages are defined in the `LanguageCode` enum.
 */
object WordToNumber {

    /**
     * Converts a word representation of a number to its integer counterpart.
     *
     * @param word The word representation of the number.
     * @param languages Vararg parameter representing the languages to consider for conversion.
     * @return The integer representation of the word or null if the word is not recognized.
     *
     * Example:
     * wordToNumber("three", LanguageCode.ENG) returns 3
     */
    fun wordToNumber(word: String, vararg languages: LanguageCode): Int? {
        val numbers = mutableMapOf<String, Int>()

        for (language in languages) {
            when (language) {
                LanguageCode.SWE -> numbers.putAll(
                    mapOf(
                        "noll" to 0,
                        "ett" to 1,
                        "två" to 2,
                        "tre" to 3,
                        "fyra" to 4,
                        "fem" to 5,
                        "sex" to 6,
                        "sju" to 7,
                        "åtta" to 8,
                        "nio" to 9,
                        "tio" to 10,
                        "elva" to 11
                    )
                )
                LanguageCode.ENG -> numbers.putAll(
                    mapOf(
                        "zero" to 0,
                        "one" to 1,
                        "two" to 2,
                        "three" to 3,
                        "four" to 4,
                        "five" to 5,
                        "six" to 6,
                        "seven" to 7,
                        "eight" to 8,
                        "nine" to 9,
                        "ten" to 10,
                        "eleven" to 11
                    )
                )
            }
        }

        return numbers[word.lowercase(Locale.ROOT)]
    }

    /**
     * Converts a word representation of a number to its string counterpart.
     *
     * @param word The word representation of the number.
     * @param languages Vararg parameter representing the languages to consider for conversion.
     * @return The string representation of the integer value of the word or null if the word is not recognized.
     *
     * Example:
     * wordToStringNumber("three", LanguageCode.ENG) returns "3"
     */
    fun wordToStringNumber(word: String, vararg languages: LanguageCode): String? {
        val numbers = mutableMapOf<String, Int>()

        for (language in languages) {
            when (language) {
                LanguageCode.SWE -> numbers.putAll(
                    mapOf(
                        "noll" to 0,
                        "ett" to 1,
                        "två" to 2,
                        "tre" to 3,
                        "fyra" to 4,
                        "fem" to 5,
                        "sex" to 6,
                        "sju" to 7,
                        "åtta" to 8,
                        "nio" to 9,
                        "tio" to 10,
                        "elva" to 11
                    )
                )
                LanguageCode.ENG -> numbers.putAll(
                    mapOf(
                        "zero" to 0,
                        "one" to 1,
                        "two" to 2,
                        "three" to 3,
                        "four" to 4,
                        "five" to 5,
                        "six" to 6,
                        "seven" to 7,
                        "eight" to 8,
                        "nine" to 9,
                        "ten" to 10,
                        "eleven" to 11
                    )
                )
            }
        }

        return numbers[word.lowercase(Locale.ROOT)]?.toString()
    }

}

/**
 * Enum class representing supported languages for word to number conversion.
 */
enum class LanguageCode {
    SWE,
    ENG
}
