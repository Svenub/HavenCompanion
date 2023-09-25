package se.umu.svke0008.havencompanion.domain.utils

import java.time.Duration
import java.time.LocalTime

object Utils {

    /**
     * Calculates the similarity percentage between two strings based on the Levenshtein distance.
     *
     * The Levenshtein distance between two strings is the minimum number of single-character edits
     * (i.e., insertions, deletions, or substitutions) required to change one string into the other.
     * This function calculates the Levenshtein distance and then computes the similarity percentage
     * based on the formula:
     *
     * Similarity Percentage = 1 - (Levenshtein Distance / Maximum Length of the two strings)
     *
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return A value representing the similarity percentage between the two strings.
     *         The value ranges from 0.0 (completely dissimilar) to 1.0 (completely similar).
     *
     * Example:
     * For the strings "kitten" and "sitting", the function will return approximately 0.7143,
     * indicating they are about 71.43% similar.
     */
    fun levenshteinDistancePercentage(s1: String, s2: String): Double {
        val distance = Array(s1.length + 1) { IntArray(s2.length + 1) }

        for (i in 0..s1.length) {
            distance[i][0] = i
        }
        for (j in 0..s2.length) {
            distance[0][j] = j
        }

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                distance[i][j] = minOf(
                    distance[i - 1][j] + 1,  // deletion
                    distance[i][j - 1] + 1,  // insertion
                    distance[i - 1][j - 1] + cost  // substitution
                )
            }
        }

        val levenshteinDistance = distance[s1.length][s2.length]
        val longestLength = maxOf(s1.length, s2.length)
      //  println("SCORE for $s1 and $s2: " + (1 - levenshteinDistance.toDouble() / longestLength))
        return (1 - levenshteinDistance.toDouble() / longestLength)
    }

    fun getFormattedDuration(startTime: LocalTime, endTime: LocalTime): String {
        val duration: Duration = Duration.between(startTime, endTime)

        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

}