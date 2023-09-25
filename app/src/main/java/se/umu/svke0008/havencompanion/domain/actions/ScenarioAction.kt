package se.umu.svke0008.havencompanion.domain.actions

import org.burnoutcrew.reorderable.ItemPosition
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

/**
 * Represents the various events that can occur during a game scenario in Frosthaven.
 */
sealed class ScenarioAction {
    data class AddCharacter(val gameCharacter: GameCharacter) : ScenarioAction()
    data class AddMultipleCharacters(val characters: List<GameCharacter>) : ScenarioAction()
    data class DeleteCharacter(val gameCharacter: GameCharacter) : ScenarioAction()
    data class EditCharacter(val gameCharacter: GameCharacter) : ScenarioAction()
    data class InitiativeChange(val gameCharacter: GameCharacter) : ScenarioAction()
    data class MultipleInitiativeChange(val gameCharacters: List<GameCharacter>) : ScenarioAction()
    data class ToggleExhaust(val gameCharacter: GameCharacter) : ScenarioAction()
    data class MoveUpCharacter(val gameCharacter: GameCharacter) : ScenarioAction()
    data class MoveDownCharacter(val gameCharacter: GameCharacter) : ScenarioAction()
    data class DragCharacter(val fromPos: ItemPosition, val toPos: ItemPosition) : ScenarioAction()
    object StartVoiceByName : ScenarioAction()
    object StartVoiceByOrder : ScenarioAction()
    object StopListening: ScenarioAction()

    object NewRound : ScenarioAction()
    object RestoreInitiative : ScenarioAction()
    object CancelInitiative : ScenarioAction()
    object ClearCharacters : ScenarioAction()
    object PreviousCharacter: ScenarioAction()
    object NextCharacter: ScenarioAction()

}
