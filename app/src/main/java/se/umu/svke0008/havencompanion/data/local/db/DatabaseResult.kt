package se.umu.svke0008.havencompanion.data.local.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult.Companion.DEFAULT_ERROR_MESSAGE

sealed class DatabaseResult<out T> {
    object Initial : DatabaseResult<Nothing>()
    object Loading : DatabaseResult<Nothing>()
    data class Success<out T>(val data: T) : DatabaseResult<T>()
    data class Failure(val message: String, val error: Throwable?) : DatabaseResult<Nothing>()

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Failed to fetch data"

    }
}

fun <T> Flow<T>.toDatabaseResult(errorMessage: String = DEFAULT_ERROR_MESSAGE): Flow<DatabaseResult<T>> {
    return this.transform { data ->
        emit(DatabaseResult.Loading)
        emit(DatabaseResult.Success(data))
    }
        .catch { e -> emit(DatabaseResult.Failure(errorMessage, e)) }
        .flowOn(Dispatchers.IO)
}

