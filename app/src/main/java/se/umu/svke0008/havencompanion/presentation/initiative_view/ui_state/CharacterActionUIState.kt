package se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult

sealed class CharacterActionUIState<out T> {
    object Idle : CharacterActionUIState<Nothing>()
    object Loading : CharacterActionUIState<Nothing>()
    data class Success<out T>(val data: T) : CharacterActionUIState<T>()
    data class Error(val message: String) : CharacterActionUIState<Nothing>()
}


fun <T> Flow<DatabaseResult<T>>.asCharacterActionUIState(): Flow<CharacterActionUIState<T>> {
    return this
        .map { databaseResult ->
            when (databaseResult) {
                is DatabaseResult.Success -> CharacterActionUIState.Success(databaseResult.data)
                is DatabaseResult.Loading -> CharacterActionUIState.Loading
                is DatabaseResult.Failure -> CharacterActionUIState.Error(databaseResult.message)
                else -> CharacterActionUIState.Idle
            }
        }
        .onStart { emit(CharacterActionUIState.Loading) }
        .retryWhen { cause, attempt ->
            if (cause is Exception && attempt < 3) {
                delay(15_000L)
                true
            } else {
                false
            }
        }
        .catch { emit(CharacterActionUIState.Error(it.message ?: "Failed to load database")) }
}


fun <T> DatabaseResult<T>.toUIState(filter: ((T) -> T)? = null): CharacterActionUIState<T> {

    return when (this) {
        is DatabaseResult.Initial -> CharacterActionUIState.Idle
        is DatabaseResult.Loading -> CharacterActionUIState.Loading
        is DatabaseResult.Success -> {
            val filteredData = filter?.invoke(this.data) ?: this.data
            CharacterActionUIState.Success(filteredData)
        }
        is DatabaseResult.Failure -> CharacterActionUIState.Error(message)
    }
}
