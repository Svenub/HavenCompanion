package se.umu.svke0008.havencompanion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.data.repository.PreferencesRepository
import se.umu.svke0008.havencompanion.domain.actions.SettingsAction
import se.umu.svke0008.havencompanion.data.local.settings.SettingState
import se.umu.svke0008.havencompanion.data.utils.DataResult
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesRepository: PreferencesRepository) :
    ViewModel() {


    val settingsState: StateFlow<DataResult<SettingState>> = preferencesRepository.getSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DataResult.Loading)


    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ToggleDarkMode -> viewModelScope.launch {
                preferencesRepository.toggleDarkMode(action.value)
            }

            is SettingsAction.ToggleHavenFont -> viewModelScope.launch {
                preferencesRepository.toggleHavenFont(action.value)
            }

            is SettingsAction.TogglePartialResultsDialog -> viewModelScope.launch {
                preferencesRepository.toggleShowPartialResultsDialog(action.value)
            }

            is SettingsAction.ToggleDynamicColor ->viewModelScope.launch {
                preferencesRepository.toggleDynamicColors(action.value)
            }
            is SettingsAction.ChangeThemeColor -> viewModelScope.launch {
                preferencesRepository.changeThemeColor(action.theme)
            }

            is SettingsAction.KeepScreenOn -> viewModelScope.launch {
                preferencesRepository.toggleScreenOn(action.value)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("onCleared", "onCleared: settings viewmodel cleared")

    }
}
