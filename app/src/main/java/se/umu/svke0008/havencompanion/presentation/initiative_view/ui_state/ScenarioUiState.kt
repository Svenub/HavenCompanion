package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver


data class ScenarioUiState(
    val showSortedCharacters: MutableState<Boolean> = mutableStateOf(false),
    val showCharacterOrdering: MutableState<Boolean> = mutableStateOf(false),
    val showExhaustedCharacters: MutableState<Boolean> = mutableStateOf(false)
)

val scenarioStateSaver = listSaver(
    save = { size ->
        listOf(
            size.showSortedCharacters,
            size.showCharacterOrdering,
            size.showExhaustedCharacters,
        )
    },
    restore = {
        ScenarioUiState(it[0], it[1], it[2])
    }
)


