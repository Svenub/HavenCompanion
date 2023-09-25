package se.umu.svke0008.havencompanion.domain.model.game_character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents the various states a character can be in during gameplay.
 * This class is designed to be Parcelable, allowing it to be passed between Android components.
 */
sealed class CharacterState : Parcelable {

    /**
     * Represents the different states a hero character can be in.
     */
    sealed class HeroState : CharacterState() {
        @Parcelize
        object Normal : HeroState()

        @Parcelize
        object Pending : HeroState()

        @Parcelize
        object Exhausted : HeroState()

        @Parcelize
        object LongResting : HeroState()
    }

    /**
     * Represents the different states a monster character can be in.
     */
    sealed class MonsterState : CharacterState() {
        @Parcelize
        object Normal : MonsterState()

        @Parcelize
        object Pending : MonsterState()

        @Parcelize
        object Exhausted : MonsterState()
    }

    companion object {
        const val LONG_REST = "long rest"
        const val EXHAUSTED = "exhausted"
    }
}

/**
 * Extension function for the String class.
 * Converts a camelCase string to a space-separated words format.
 * For example, "camelCaseToWords" becomes "camel Case To Words".
 *
 * @return A string where each capitalized letter in the original string is preceded by a space.
 */
fun String.camelCaseToWords(): String {
    return this.replace(Regex("(?<!^)([A-Z])"), " $1")
}