package se.umu.svke0008.havencompanion.domain.model.game_character

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.parcelize.Parcelize
import se.umu.svke0008.havencompanion.ui.theme.MONSTER_COLOR_RED

/**
 * Represents a game character in Frosthaven. This is a sealed class that can be either a Hero or a Monster.
 * Each character has a name, potential aliases, initiative values, a state, and a color.
 *
 * @property characterName The name of the character.
 * @property nameAlias A list of alternative names or pronunciations for the character.
 * @property firstInitiative The primary initiative value of the character.
 * @property onConflictOrder An order value used to resolve initiative conflicts.
 * @property characterState The current state of the character (e.g., Normal, Pending, Exhausted).
 * @property colorInt The integer representation of the character's color.
 */
sealed class GameCharacter(
    open val id: Int,
    open val characterName: String,
    open val nameAlias: List<String> = emptyList(),
    open val firstInitiative: Int? = null,
    open val onConflictOrder: Int? = null,
    open val characterState: CharacterState,
    @ColorInt open val colorInt: Int,

    ) : Parcelable {
    val color: Color
        get() = Color(colorInt)


    /**
     * Represents a hero character in the game.
     *
     * @property secondInitiative The secondary initiative value of the hero.
     */
    @Parcelize
    data class Hero(
        override val id: Int = 0,
        override val characterName: String,
        override val nameAlias: List<String> = emptyList(),
        override val firstInitiative: Int? = null,
        val secondInitiative: Int? = null,
        override val onConflictOrder: Int? = null,
        override val characterState: CharacterState.HeroState = CharacterState.HeroState.Normal,
        @ColorInt override val colorInt: Int,
    ) : GameCharacter(
        id = id,
        characterName = characterName,
        colorInt = colorInt,
        characterState = characterState,
        onConflictOrder = onConflictOrder
    ) {
        override fun toString(): String {
            return "Hero(id=$id, characterName='$characterName', firstInitiative=$firstInitiative, secondInitiative=$secondInitiative, onConflictOrder=$onConflictOrder)"
        }
    }

    /**
     * Represents a monster character in the game.
     */
    @Parcelize
    data class Monster(
        override val id: Int = 1,
        override val characterName: String,
        override val nameAlias: List<String> = emptyList(),
        override val firstInitiative: Int? = null,
        override var onConflictOrder: Int? = null,
        override val characterState: CharacterState.MonsterState = CharacterState.MonsterState.Normal,
        @ColorInt override val colorInt: Int = MONSTER_COLOR_RED.toArgb(),
    ) : GameCharacter(
        id = id,
        characterName = characterName,
        colorInt = colorInt,
        characterState = characterState,
        onConflictOrder = onConflictOrder
    ) {
        override fun toString(): String {
            return "Monster(id=$id, characterName='$characterName', firstInitiative=$firstInitiative, onConflictOrder=$onConflictOrder)"
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameCharacter

        if (id != other.id) return false
        if (characterName != other.characterName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + characterName.hashCode()
        return result
    }


}

/**
 * Checks if the game character is in a "Pending" state.
 *
 * @return True if the character is pending, false otherwise.
 */
fun GameCharacter.isPending(): Boolean {
    return when (this) {
        is GameCharacter.Hero -> this.characterState == CharacterState.HeroState.Pending
        is GameCharacter.Monster -> this.characterState == CharacterState.MonsterState.Pending
    }
}

/**
 * Checks if the game character is in an "Exhausted" state.
 *
 * @return True if the character is exhausted, false otherwise.
 */
fun GameCharacter.isExhausted(): Boolean {
    return when (this) {
        is GameCharacter.Hero -> this.characterState == CharacterState.HeroState.Exhausted
        is GameCharacter.Monster -> this.characterState == CharacterState.MonsterState.Exhausted
    }
}

/**
 * Checks if the hero character is in a "Long Resting" state.
 *
 * @return True if the hero is long resting, false otherwise.
 */
fun GameCharacter.Hero.isLongResting(): Boolean {
    return this.characterState == CharacterState.HeroState.LongResting
}

/**
 * Checks if a game character is in a list collection of [GameCharacter] based on its id or character name
 *
 * @return True if a character is found
 */
fun List<GameCharacter>.containsInList(element: GameCharacter): Boolean {
    val found =
        this.find { char -> char.id == element.id}
    found?.let {
        return true
    }
    return false
}


