package se.umu.svke0008.havencompanion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.domain.model.scenario.InitiativeState
import se.umu.svke0008.havencompanion.domain.actions.ScenarioAction
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState.Companion.DATE
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState.Companion.GAME_CHARACTERS
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState.Companion.INITIATIVE_STATE
import se.umu.svke0008.havencompanion.domain.model.scenario.ScenarioState.Companion.ROUNDS_DONE
import se.umu.svke0008.havencompanion.domain.model.speechRecognition.SpeechRecognition
import se.umu.svke0008.havencompanion.domain.model.speech_to_command.ByName
import se.umu.svke0008.havencompanion.domain.model.speech_to_command.ByOrder
import se.umu.svke0008.havencompanion.domain.model.speech_to_command.CommandResult
import se.umu.svke0008.havencompanion.domain.model.speech_to_command.SpeechToCommand
import se.umu.svke0008.havencompanion.domain.service.ScenarioService
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events.ScenarioUIEvent
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScenarioViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val scenarioService: ScenarioService,
    private var speechRecognitionHelper: SpeechRecognition
) : ViewModel() {


    private val _currentCharacterIndex = MutableStateFlow(0)
    val currentCharacterIndex: StateFlow<Int> = _currentCharacterIndex.asStateFlow()

    private val _voiceActive = MutableStateFlow(false)
    val voiceActive = _voiceActive.asStateFlow()

    val isListening = speechRecognitionHelper.isListening.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    val allGameCharacter = scenarioService.allGameCharacters.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val charactersInPlay = scenarioService.charactersInPlay.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val exhaustedCharacters = scenarioService.exhaustedCharacters.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val heroes = scenarioService.heroes.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val monsters = scenarioService.monsters.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val initiativeState = scenarioService.initiativeState
            // Avoid crash when user navigates between characters when setting initiative in the dialog
        .onEach { state ->
            if (state is InitiativeState.Pending &&
                state.pendingCharacters.size == _currentCharacterIndex.value) {
                _currentCharacterIndex.update { index -> index - 1 }
            }
        }
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
        )

    val partialSpeechResult = speechRecognitionHelper.partialSpeechResultFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )


    val scenarioUIEvent: SharedFlow<ScenarioUIEvent> = combine(scenarioService.scenarioState, scenarioService.initiativeState) {
        scenarioState, initiativeState ->
            when(initiativeState) {
                InitiativeState.Cancelled -> ScenarioUIEvent.Canceled
                is InitiativeState.Done -> {
                    val startTime = scenarioState.date
                    val now = LocalTime.now()
                    ScenarioUIEvent.Done(scenarioState.roundsDone, startTime, now)
                }
                else -> ScenarioUIEvent.Initial
            }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000, 0), replay = 0)


    init {
        saveGameState()
        retrieveSavedGameState()
    }

    private fun goToNextCharacter() {
        viewModelScope.launch {
            when (val state = initiativeState.first()) {
                is InitiativeState.Pending -> {
                    if (_currentCharacterIndex.value < state.pendingCharacters.size - 1) {
                        _currentCharacterIndex.value += 1
                    }
                }

                else -> _currentCharacterIndex.update { 0 }
            }

        }
    }

    private fun goToPreviousCharacter() {
        viewModelScope.launch {
            when (initiativeState.first()) {
                is InitiativeState.Pending -> {
                    if (_currentCharacterIndex.value > 0) {
                        _currentCharacterIndex.value -= 1
                    }
                }
                else -> _currentCharacterIndex.update { 0 }
            }


        }
    }

    /**
     * Toggles the voice activation status.
     *
     *
     * @param active A boolean value indicating whether voice activation should be turned on (`true`) or off (`false`).
     */
    fun toggleVoice(active: Boolean) {
        _voiceActive.update { active }
    }


    /**
     * Observes the results from the speech recognition and processes them based on the current state of the application.
     * This function combines multiple flows to determine the appropriate action to take based on the user's voice command.
     *
     * @param speechToCommand An instance of [SpeechToCommand] which determines how the speech results should be interpreted (e.g., ByName or ByOrder).
     * @return Unit This function does not return any value but performs side effects based on the combined flows.
     * @throws Exception This function may throw exceptions related to the underlying flows or processing logic.
     *
     */
    private suspend fun observeResult(speechToCommand: SpeechToCommand) =
        combine(
            speechRecognitionHelper.speechResultFlow,
            voiceActive,
            initiativeState
        )
        { speechResult, voiceActive, initiativeState ->
          //  val oneNumber = speechResult.isNotEmpty() && speechResult.first().split(" ").size == 1

            if (voiceActive && initiativeState is InitiativeState.Pending) {
                val result = when (speechToCommand) {
                    is ByName -> speechToCommand.execute(
                        speechResult,
                        listOf(initiativeState.pendingCharacter)
                    )

                    is ByOrder -> speechToCommand.execute(
                        speechResult,
                        initiativeState.pendingCharacters
                    )

                    else -> emptyList()
                }

                for (command in result) {
                    when (command) {
                        is CommandResult.InitiativeCommand -> scenarioService.setFirstInitiative(
                            command.gameCharacter,
                            command.initiative
                        )

                        is CommandResult.InitiativeStateCommand -> scenarioService.changeCharacterState(
                            command.gameCharacter,
                            command.characterState
                        )
                    }
                }
            }
        }.collectLatest { }


    fun onAction(action: ScenarioAction) {
        when (action) {
            is ScenarioAction.AddCharacter -> scenarioService.addGameCharacter(action.gameCharacter)
            is ScenarioAction.AddMultipleCharacters -> scenarioService.addMultipleCharacters(action.characters)
            is ScenarioAction.DeleteCharacter -> scenarioService.removeGameCharacter(action.gameCharacter)
            is ScenarioAction.EditCharacter -> scenarioService.editGameCharacter(action.gameCharacter)
            is ScenarioAction.MoveDownCharacter -> scenarioService.moveCharacterDown(action.gameCharacter)
            is ScenarioAction.MoveUpCharacter -> scenarioService.moveCharacterUp(action.gameCharacter)
            is ScenarioAction.NewRound -> viewModelScope.launch {
                scenarioService.startNewRound()
            }

            is ScenarioAction.MultipleInitiativeChange -> scenarioService.initiativeChange(action.gameCharacters)
            is ScenarioAction.InitiativeChange -> scenarioService.initiativeChange(action.gameCharacter)
            is ScenarioAction.ToggleExhaust -> scenarioService.toggleExhausted(action.gameCharacter)
            ScenarioAction.ClearCharacters -> scenarioService.clearCharacters()
            is ScenarioAction.DragCharacter -> scenarioService.moveCharacter(
                action.fromPos,
                action.toPos
            )

            ScenarioAction.CancelInitiative -> {
                scenarioService.cancelInitiative()
                _currentCharacterIndex.update { 0 }
            }
            ScenarioAction.StartVoiceByName -> viewModelScope.launch {
                scenarioService.startNewRound()
                speechRecognitionHelper.startListening()
                observeResult(ByName())
            }

            ScenarioAction.StartVoiceByOrder -> viewModelScope.launch {
                scenarioService.startNewRound()
                speechRecognitionHelper.startListening()
                observeResult(ByOrder())
            }

            ScenarioAction.StopListening -> speechRecognitionHelper.stopListening()
            ScenarioAction.NextCharacter -> goToNextCharacter()
            ScenarioAction.PreviousCharacter -> goToPreviousCharacter()
            ScenarioAction.RestoreInitiative -> scenarioService.restoreInitiativeState()
        }
    }

    /**
     * Saves the current state of the scenario by launching a coroutine that collects the scenario state
     * and assigns it to the corresponding keys in the saved state handle.
     */
    private fun saveGameState() {
        viewModelScope.launch {
            scenarioService.scenarioState.collect { scenarioState ->
                savedStateHandle[INITIATIVE_STATE] = scenarioState.initiativeState
                savedStateHandle[GAME_CHARACTERS] = scenarioState.allGameCharacters
                savedStateHandle[ROUNDS_DONE] = scenarioState.roundsDone
                savedStateHandle[DATE] = scenarioState.date
            }
        }
    }

    /**
     * Retrieves the saved state of the scenario. If no saved state exists for a certain key,
     * it assigns a default value. The restored scenario state is then passed on to the scenario service.
     */
    private fun retrieveSavedGameState() {
        val restoredGameState = ScenarioState(
            initiativeState = savedStateHandle[INITIATIVE_STATE] ?: InitiativeState.Initial,
            allGameCharacters = savedStateHandle[GAME_CHARACTERS] ?: emptyList(),
            roundsDone = savedStateHandle[ROUNDS_DONE]?: 0,
            date = savedStateHandle[DATE] ?: LocalTime.now(),
        )
        scenarioService.retrieveSavedScenarioState(restoredGameState)
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("onCleared", "onCleared: scenario viewemodel cleared");
    }
}