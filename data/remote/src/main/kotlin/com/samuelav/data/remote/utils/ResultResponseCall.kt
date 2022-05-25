package com.samuelav.data.remote.utils

import com.samuelav.common.Error
import com.samuelav.common.Result
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.*

internal class ResultResponseCall<S : Any>(
    private val call: Call<S>,
    private val errorConverter: Converter<ResponseBody, Error>
): Call<Result<Error, S>> {
    override fun enqueue(callback: Callback<Result<Error, S>>) {
        synchronized(this) {
            call.enqueue(
                object :Callback<S> {
                    override fun onResponse(call: Call<S>, response: Response<S>) {
                        val body = response.body()
                        val result =
                            when {
                                response.isSuccessful && body == null && body !is Unit ->
                                    Result.Failure(Error.NoContent)
                                response.isSuccessful && body != null ->
                                    Result.Success(body)
                                else -> Result.Failure(
                                    response.handleCustomApiError(errorConverter = errorConverter))
                            }

                        callback.onResponse(
                            this@ResultResponseCall,
                            Response.success(result))
                    }

                    override fun onFailure(call: Call<S>, t: Throwable) {
                        val error =
                            when (t) {
                                is HttpException -> t.handleHttpException()
                                else -> Error.Network
                            }

                        callback.onResponse(
                            this@ResultResponseCall,
                            Response.success(Result.Failure(error)))
                    }
                }
            )
        }
    }

    override fun isExecuted(): Boolean = synchronized(this) { call.isExecuted }
    override fun clone(): Call<Result<Error, S>> = ResultResponseCall(call.clone(), errorConverter)
    override fun isCanceled(): Boolean  = synchronized(this) { call.isCanceled }
    override fun cancel() = synchronized(this) { call.cancel() }
    override fun execute(): Response<Result<Error, S>> =
        throw UnsupportedOperationException("Suspend call does not support synchronous execution")
    override fun request(): Request = call.request()
    override fun timeout(): Timeout = call.timeout()

    // TODO: Modify API errors
    private fun HttpException.handleHttpException(): Error =
        when (code()) {
            400 -> Error.BadRequest
            403 -> Error.Forbidden
            404 -> Error.NotFound
            408 -> Error.Timeout
            500 -> Error.Server
            502 -> Error.BadGateway
            503 -> Error.ServiceUnavailable
            else -> Error.Unknown
        }
    private fun <S> Response<S>.handleCustomApiError(
        errorConverter: Converter<ResponseBody, Error>
    ): Error =
        try {
            errorConverter.convert(errorBody()!!)!!
        } catch (ex: Exception) {
            Error.Unknown
        }
}