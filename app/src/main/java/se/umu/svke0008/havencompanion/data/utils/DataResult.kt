package se.umu.svke0008.havencompanion.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult

sealed class DataResult<out T> {

    object Loading : DataResult<Nothing>()
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failure(val message: String = DEFAULT_ERROR_MESSAGE, val error: Throwable?) : DataResult<Nothing>()

    companion object {
        const val DEFAULT_ERROR_MESSAGE = "Failed to fetch data"

    }
}


fun <T> Flow<T>.toDataResult(errorMessage: String = DataResult.DEFAULT_ERROR_MESSAGE): Flow<DataResult<T>> {
    return this.transform { data ->
        emit(DataResult.Loading)
        emit(DataResult.Success(data))
    }
        .catch { e -> emit(DataResult.Failure(errorMessage, e)) }
        .flowOn(Dispatchers.IO)
}