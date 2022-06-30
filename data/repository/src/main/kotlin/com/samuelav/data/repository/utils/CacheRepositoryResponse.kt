package com.samuelav.data.repository.utils

import com.samuelav.common.utils.Error
import com.samuelav.common.utils.Result
import com.samuelav.common.utils.ifSuccess
import com.samuelav.common.utils.isSuccess
import kotlinx.coroutines.coroutineScope

abstract class CacheRepositoryResponse<T> {

    abstract fun shouldFetchFromRemote(dataFromLocal: T?): Boolean

    abstract suspend fun loadFromLocal(): T?

    abstract suspend fun loadFromRemote(): Result<Error, T>

    abstract suspend fun saveRemoteResult(dataFromRemote: T)

    suspend fun execute(forceFetch: Boolean = false): Result<Error, T> =
        coroutineScope {
            try {
                val dbResult = loadFromLocal()

                if (dbResult == null || forceFetch || shouldFetchFromRemote(dbResult)) {
                    val networkResult = loadFromRemote()
                    networkResult.ifSuccess { saveRemoteResult(it) }
                    loadFromLocal()?.takeIf { networkResult.isSuccess }?.let { Result.Success(it) }
                        ?: networkResult as? Result.Failure
                        ?: Result.Failure(Error.NotFound)
                } else {
                    Result.Success(dbResult)
                }
            } catch (e: Exception) {
                Result.Failure(Error.Unknown)
            }
        }
}