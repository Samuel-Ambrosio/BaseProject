package com.samuelav.domain.repository.utils

import com.samuelav.domain.model.extensions.isNull
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.model.utils.asFailure
import com.samuelav.domain.model.utils.fold
import com.samuelav.domain.model.utils.mapSuccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformLatest

suspend inline fun <reified T> cachedRemoteResource(
    crossinline getFromLocal: suspend () -> T?,
    crossinline shouldFetch: (T) -> Boolean = { false },
    crossinline getFromRemote: suspend () -> Result<Error, T>,
    crossinline saveToLocal: suspend (T) -> Unit,
): Result<Error, T> =
    cachedRemoteResource<T, T>(
        getFromLocal = getFromLocal,
        shouldFetch = shouldFetch,
        getFromRemote = getFromRemote,
        saveToLocal = saveToLocal,
    )

@JvmName("cachedRemoteResourceR")
suspend inline fun <reified T, reified R> cachedRemoteResource(
    crossinline getFromLocal: suspend () -> T?,
    crossinline shouldFetch: (T) -> Boolean = { false },
    crossinline getFromRemote: suspend () -> Result<Error, R>,
    crossinline saveToLocal: suspend (R) -> Unit,
): Result<Error, T> = coroutineScope {
    try {
        val cachedResult = getFromLocal()

        if (cachedResult.isNull() || shouldFetch(cachedResult)) {
            getFromRemote().mapSuccess {
                saveToLocal(it)
                getFromLocal()
            }
        } else {
            Result.Success(cachedResult)
        }
    } catch (e: Exception) {
        Result.Failure(Error.Unknown)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T> cachedRemoteResourceFlow(
    crossinline getFromLocal: () -> Flow<T?>,
    crossinline getFromRemote: suspend () -> Result<Error, T>,
    crossinline saveToLocal: suspend (T) -> Unit,
): Flow<Result<Error, T>> =
    getFromLocal().transformLatest { cachedResult ->
        if (cachedResult.isNull()) {
            getFromRemote()
                .fold(
                    isSuccess = { saveToLocal(it) },
                    isLoading = { emit(Result.Loading) },
                    isFailure = { emit(it.asFailure()) },
                )
        } else {
            emit(Result.Success(cachedResult))
        }
    }