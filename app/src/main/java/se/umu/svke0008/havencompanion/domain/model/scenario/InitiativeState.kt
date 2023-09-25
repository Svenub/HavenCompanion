package se.umu.svke0008.havencompanion.domain.model.scenario

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

/**
 * Represents the various states of initiative during a game round in Frosthaven.
 */
sealed class InitiativeState : Parcelable {

    /**
     * Represents the initial state of initiative at the start of the game.
     */
    @Parcelize
    object Initial : InitiativeState()

    /**
     * Represents the beginning of a new round.
     */
    @Parcelize
    object NewRound : InitiativeState()

    /**
     * Represents a cancelled initiative state.
     */
    @Parcelize
    object Cancelled : InitiativeState()

    /**
     * Represents a state where a character's initiative is pending.
     * @property pendingCharacter The character whose initiative is pending.
     * @property pendingCharacters List of characters with pending initiatives.
     */
    @Parcelize
    data class Pending(
        val pendingCharacter: GameCharacter,
        val pendingCharacters: List<GameCharacter>
    ) : InitiativeState()

    /**
     * Represents a state where a hero's second initiative needs to be set.
     * @property gameCharacter The hero whose second initiative is to be set.
     */
    @Parcelize
    data class SetSecondInitiative(val gameCharacter: GameCharacter.Hero) : InitiativeState()

    /**
     * Represents a conflict in initiative values among heroes.
     * @property conflictingHeroes List of heroes with conflicting initiatives.
     */
    @Parcelize
    data class HeroConflict(val conflictingHeroes: List<GameCharacter.Hero>) : InitiativeState()

    /**
     * Represents a conflict among heroes who have declared a "Long Rest".
     * @property conflictingHeroes List of heroes with conflicting "Long Rest" declarations.
     */
    @Parcelize
    data class LongRestConflict(val conflictingHeroes: List<GameCharacter.Hero>) : InitiativeState()

    /**
     * Represents a conflict in initiative values among monsters.
     * @property conflictingMonsters List of monsters with conflicting initiatives.
     */
    @Parcelize
    data class MonsterConflict(val conflictingMonsters: List<GameCharacter.Monster>) :
        InitiativeState()

    /**
     * Represents the completion of initiative setting for the current round.
     */
    @Parcelize
    object Done : InitiativeState()
}
