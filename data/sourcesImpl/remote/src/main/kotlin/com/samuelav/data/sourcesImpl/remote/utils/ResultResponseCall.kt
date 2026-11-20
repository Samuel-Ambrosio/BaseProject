package com.samuelav.data.sourcesImpl.remote.utils

import com.samuelav.data.sourcesImpl.remote.models.NetworkApiError
import com.samuelav.data.sourcesImpl.remote.models.toError
import com.samuelav.domain.model.extensions.isNotNull
import com.samuelav.domain.model.extensions.isNull
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.model.utils.debug.AppLogger
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.time.format.DateTimeParseException

internal class ResultResponseCall<S : Any>(
    private val call: Call<S>,
    private val errorConverter: Converter<ResponseBody, NetworkApiError>,
) : Call<Result<Error, S>> {
    override fun enqueue(callback: Callback<Result<Error, S>>) {
        synchronized(this) {
            call.enqueue(
                object : Callback<S> {
                    override fun onResponse(call: Call<S>, response: Response<S>) {
                        val body = response.body()
                        val result =
                            when {
                                response.isSuccessful && body.isNull() ->
                                    Result.Failure(Error.NoContent)
                                response.isSuccessful && body.isNotNull() ->
                                    Result.Success(body)
                                else -> Result.Failure(
                                    response.handleApiError(errorConverter = errorConverter)
                                )
                            }

                        callback.onResponse(
                            this@ResultResponseCall,
                            Response.success(result),
                        )
                    }

                    override fun onFailure(call: Call<S>, t: Throwable) {
                        val error =
                            when (t) {
                                is HttpException ->
                                    t.response()?.handleApiError(errorConverter = errorConverter)
                                        ?: t.code().toHttpError()
                                is JsonDataException -> Error.JsonSyntax
                                is JsonEncodingException -> Error.JsonSyntax
                                is DateTimeParseException -> Error.DateTimeParse
                                else -> Error.Network
                            }

                        AppLogger.e(t, "Error: $error")

                        callback.onResponse(
                            this@ResultResponseCall,
                            Response.success(Result.Failure(error)),
                        )
                    }
                }
            )
        }
    }

    override fun isExecuted(): Boolean = synchronized(this) { call.isExecuted }
    override fun clone(): Call<Result<Error, S>> = ResultResponseCall(call.clone(), errorConverter)
    override fun isCanceled(): Boolean = synchronized(this) { call.isCanceled }
    override fun cancel() = synchronized(this) { call.cancel() }
    override fun execute(): Response<Result<Error, S>> =
        throw UnsupportedOperationException("Suspend call does not support synchronous execution")
    override fun request(): Request = call.request()
    override fun timeout(): Timeout = call.timeout()

    private fun Int.toHttpError(): Error =
        when (this) {
            400 -> Error.BadRequest
            403 -> Error.Forbidden
            404 -> Error.NotFound
            408 -> Error.Timeout
            502 -> Error.BadGateway
            503 -> Error.ServiceUnavailable
            in 500..599 -> Error.Server
            else -> Error.Unknown
        }

    private fun <S> Response<S>.handleApiError(
        errorConverter: Converter<ResponseBody, NetworkApiError>
    ): Error {
        val apiError =
            try {
                errorBody()?.let { errorConverter.convert(it)?.toError() }
            } catch (@Suppress("TooGenericExceptionCaught") _: Exception) {
                null
            }
        return if (apiError?.errors.isNullOrEmpty()) code().toHttpError() else apiError
    }
}
