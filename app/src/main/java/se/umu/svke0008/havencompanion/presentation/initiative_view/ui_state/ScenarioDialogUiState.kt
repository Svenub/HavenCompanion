package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter


data class ScenarioDialogUiState(
    val showAddCharacterDialog: MutableState<Boolean> = mutableStateOf(false),
    val showEditCharacterDialog: MutableState<Boolean> = mutableStateOf(false),
    val showDeleteCharacterDialog: MutableState<Boolean> = mutableStateOf(false),
    val showScenarioOptionsDialog: MutableState<Boolean> = mutableStateOf(false),
    val showInitiativeDialog: MutableState<Boolean> = mutableStateOf(false)
){
    fun showInitiativeDialog(gameCharacter: GameCharacter) {

    }
}


val dialogStateSaver = listSaver(
    save = { size ->
        listOf(
            size.showAddCharacterDialog,
            size.showEditCharacterDialog,
            size.showDeleteCharacterDialog,
            size.showScenarioOptionsDialog,
            size.showInitiativeDialog
        )
    },
    restore = {
        ScenarioDialogUiState(it[0], it[1], it[2], it[3], it[4])
    }
)
