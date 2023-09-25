package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun rememberScenarioUiState(): ScenarioUiState {
    return  rememberSaveable(saver = scenarioStateSaver){ ScenarioUiState() }
}

