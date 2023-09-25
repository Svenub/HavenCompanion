package se.umu.svke0008.havencompanion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.domain.actions.CharacterAction
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.repository.CharacterRepository
import se.umu.svke0008.havencompanion.domain.repository.CharacterRepository.Companion.RESTORE_CHARACTER
import se.umu.svke0008.havencompanion.domain.utils.NameValidator
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events.CharacterOperationUIEvent
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state.CharacterActionUIState
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state.asCharacterActionUIState
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state.toUIState
import java.util.Locale
import javax.inject.Inject

/**
 * A ViewModel responsible for managing character data.
 * This ViewModel uses Hilt for dependency injection to obtain an instance of [CharacterRepository].
 *
 * @property repo An instance of [CharacterRepository] injected by Hilt.
 */
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repo: CharacterRepository,
    private val validator: NameValidator,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _validationResult = MutableSharedFlow<ValidationResult>()
    val validationResult = _validationResult
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    private val _characterOperationUIEvent = MutableSharedFlow<CharacterOperationUIEvent>()
    val characterOperationUIEvent = _characterOperationUIEvent
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val heroEntities: StateFlow<CharacterActionUIState<List<GameCharacterEntity>>> =
        combine(searchText, repo.getHeroEntities()) { query, dataBaseResult ->
            dataBaseResult.toUIState { list ->
                list.filter {
                    (it.revealed && it.characterName.lowercase(Locale.ROOT)
                        .contains(query.lowercase(Locale.ROOT))) || !it.revealed
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterActionUIState.Idle)



    val monsterEntities: StateFlow<CharacterActionUIState<List<GameCharacterEntity>>> =
        combine(searchText, repo.getMonsterEntities()) { query, dataBaseResult ->
            dataBaseResult.toUIState { list ->
                list.filter {
                    (it.revealed && it.characterName.lowercase(Locale.ROOT)
                        .contains(query.lowercase(Locale.ROOT))) || !it.revealed
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterActionUIState.Idle)


    private val allCharacters: StateFlow<CharacterActionUIState<List<GameCharacter>>> =
        repo.getAllCharacters()
            .asCharacterActionUIState()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CharacterActionUIState.Idle
            )


    private val restoreCharacter: StateFlow<GameCharacterEntity?> =
        savedStateHandle.getStateFlow(RESTORE_CHARACTER, null)


    fun onAction(action: CharacterAction) {
        when (action) {
            is CharacterAction.CreateCharacter -> {
                viewModelScope.launch {
                    try {
                        val currentCharacters = withTimeout(5000) {
                            allCharacters.first { it is CharacterActionUIState.Success } as CharacterActionUIState.Success
                        }
                        val allNamesToCheck =
                            currentCharacters.data.flatMap {
                                listOf(it.characterName) + it.nameAlias
                            }

                        val nameValidationResults = validator.isNameValid(
                            action.character.characterName,
                            allNamesToCheck,
                            checkingAlias = false
                        )

                        if (nameValidationResults !is ValidationResult.Valid) {
                            _validationResult.emit(nameValidationResults)
                            return@launch
                        }

                        for (alias in action.character.nameAlias.split(',')) {
                            val result =
                                validator.isNameValid(alias, allNamesToCheck, checkingAlias = true)
                            if (result is ValidationResult.Invalid.AliasTooSimilar) {
                                _validationResult.emit(result)
                                return@launch
                            }
                        }



                        repo.createNewCharacter(action.character).collect { createResult ->
                            when (createResult) {
                                DatabaseResult.Initial ->
                                    _characterOperationUIEvent
                                        .emit(CharacterOperationUIEvent.NoEvent)

                                DatabaseResult.Loading -> _characterOperationUIEvent
                                    .emit(CharacterOperationUIEvent.Loading)

                                is DatabaseResult.Success -> {
                                    _characterOperationUIEvent
                                        .emit(CharacterOperationUIEvent.CreateCharacterUIEvent.Success())
                                    _validationResult.emit(ValidationResult.Valid)
                                    // Emit again and delay to have the add character screen collect result
                                    delay(100)
                                    _characterOperationUIEvent
                                        .emit(CharacterOperationUIEvent.CreateCharacterUIEvent.Success())
                                }

                                is DatabaseResult.Failure -> _characterOperationUIEvent
                                    .emit(CharacterOperationUIEvent.CreateCharacterUIEvent.Error())
                            }
                        }


                    } catch (e: TimeoutCancellationException) {
                        _validationResult.emit(
                            ValidationResult.Invalid.Other("Timeout while waiting for characters")
                        )
                    }
                }
            }

            is CharacterAction.UpdateCharacter -> viewModelScope.launch {
                try {
                    val currentCharacters = withTimeout(5000) {
                        allCharacters.first { it is CharacterActionUIState.Success } as CharacterActionUIState.Success
                    }


                    val allNamesToCheckExcludeThisCharacter =
                        currentCharacters.data.filter { it.id != action.character.id }
                            .flatMap {
                                listOf(it.characterName) + it.nameAlias
                            }

                    val nameValidationResults =
                        validator.isNameValid(
                            action.character.characterName,
                            allNamesToCheckExcludeThisCharacter,
                            checkingAlias = false
                        )

                    if (nameValidationResults !is ValidationResult.Valid) {
                        _validationResult.emit(nameValidationResults)
                        return@launch
                    }



                    for (alias in action.character.nameAlias.split(',')) {
                        val result = validator.isNameValid(
                            alias,
                            allNamesToCheckExcludeThisCharacter,
                            checkingAlias = true
                        )
                        if (result is ValidationResult.Invalid.AliasTooSimilar) {
                            _validationResult.emit(result)
                            return@launch
                        }
                    }

                    _validationResult.emit(ValidationResult.Valid)



                    repo.updateCharacter(action.character).collect { updateResult ->
                        when (updateResult) {
                            DatabaseResult.Initial -> _characterOperationUIEvent.emit(
                                CharacterOperationUIEvent.NoEvent
                            )

                            DatabaseResult.Loading -> _characterOperationUIEvent.emit(
                                CharacterOperationUIEvent.Loading
                            )

                            is DatabaseResult.Failure -> _characterOperationUIEvent.emit(
                                CharacterOperationUIEvent.UpdateCharacterUIEvent.Error()
                            )

                            is DatabaseResult.Success -> _characterOperationUIEvent.emit(
                                CharacterOperationUIEvent.UpdateCharacterUIEvent.Success()
                            )
                        }
                    }

                } catch (e: TimeoutCancellationException) {
                    _validationResult.emit(
                        ValidationResult.Invalid.Other("Timeout while waiting for characters")
                    )
                }
            }

            is CharacterAction.DeleteCharacter ->
                viewModelScope.launch {
                    repo.deleteCharacter(action.character).collect { result ->
                        when (result) {
                            DatabaseResult.Initial ->
                                _characterOperationUIEvent.emit(CharacterOperationUIEvent.NoEvent)

                            DatabaseResult.Loading ->
                                _characterOperationUIEvent.emit(CharacterOperationUIEvent.Loading)

                            is DatabaseResult.Failure ->
                                _characterOperationUIEvent.emit(CharacterOperationUIEvent.DeleteCharacterUIEvent.Error())

                            is DatabaseResult.Success ->
                                _characterOperationUIEvent.emit(
                                    CharacterOperationUIEvent.DeleteCharacterUIEvent.Success()
                                )
                        }
                    }
                }

            is CharacterAction.LockCharacter -> viewModelScope.launch {
                repo.updateCharacter(action.character.copy(revealed = false))
                    .launchIn(viewModelScope)

            }

            is CharacterAction.UnLockCharacter -> viewModelScope.launch {
                repo.updateCharacter(action.character.copy(revealed = true))
                    .launchIn(viewModelScope)
            }

            CharacterAction.UndoDelete -> viewModelScope.launch {
                val character = restoreCharacter.firstOrNull()
                character?.let { undoCharacter ->
                    repo.createNewCharacter(undoCharacter.copy(id = null)).launchIn(viewModelScope)
                }
            }

            CharacterAction.UndoEdit -> {
                viewModelScope.launch {
                    val character = restoreCharacter.firstOrNull()
                    character?.let { undoCharacter ->
                        repo.updateCharacter(undoCharacter).launchIn(viewModelScope)
                    }
                }
            }
        }
    }

    fun updateSearchText(query: String) {
        _searchText.update { query }
    }

    fun resetValidationResult() {
        viewModelScope.launch {
            _validationResult.emit(ValidationResult.Initial)
        }
    }

    fun storeCharacter(gameCharacterEntity: GameCharacterEntity) {
        savedStateHandle[RESTORE_CHARACTER] = gameCharacterEntity
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("ViewModel", "character viewmodel cleared")
    }

}
