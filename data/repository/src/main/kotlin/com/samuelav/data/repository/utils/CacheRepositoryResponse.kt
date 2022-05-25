package com.samuelav.data.repository.utils

import com.samuelav.common.Error
import com.samuelav.common.Result
import com.samuelav.common.ifSuccess
import kotlinx.coroutines.coroutineScope

abstract class CacheRepositoryResponse<LocalResult, RemoteResult> {

    abstract fun shouldFetchFromRemote(data: LocalResult?): Boolean

    abstract suspend fun loadFromRemote(): Result<Error, RemoteResult>

    abstract suspend fun saveRemoteResult(result: RemoteResult)

    abstract suspend fun loadFromLocal(): LocalResult?

    suspend fun execute(forceFetch: Boolean = false): Result<Error, LocalResult> =
        coroutineScope {
            try {
                val dbResult = loadFromLocal()

                if (dbResult == null || forceFetch || shouldFetchFromRemote(dbResult)) {
                    val networkResult = loadFromRemote()
                    networkResult.ifSuccess { saveRemoteResult(it) }
                    loadFromLocal()?.let { Result.Success(it) }
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