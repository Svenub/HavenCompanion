package se.umu.svke0008.havencompanion.domain.model.scenario

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import java.time.LocalTime


/**
 * Represents the current state of a game scenario in Frosthaven.
 *
 * @property initiativeState The current state of initiative for the scenario.
 * @property allGameCharacters List of all characters participating in the scenario.
 * @property roundsDone Number of rounds that have been completed.
 * @property date The local time when the scenario state was created or updated.
 */
@Parcelize
data class ScenarioState(
    val initiativeState: InitiativeState = InitiativeState.Initial,
    val allGameCharacters: List<GameCharacter> = emptyList(),
    val roundsDone: Int = 0,
    val date: LocalTime = LocalTime.now()
): Parcelable {

    companion object {
        fun getInstance(): ScenarioState = ScenarioState()
        const val INITIATIVE_STATE = "INITIATIVE_STATE"
        const val GAME_CHARACTERS = "GAME_CHARACTERS"
        const val ROUNDS_DONE = "ROUNDS_DONE"
        const val DATE = "DATE"
    }
}