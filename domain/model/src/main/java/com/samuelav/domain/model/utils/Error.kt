package com.samuelav.domain.model.utils

sealed class Error {
    object NoContent: Error()
    object BadRequest: Error()
    object Forbidden: Error()
    object NotFound: Error()
    object Timeout: Error()
    object Server: Error()
    object BadGateway: Error()
    object ServiceUnavailable: Error()

    object JsonSyntax: Error()
    object DateTimeParse: Error()
    object Network: Error()
    object Unknown: Error()

    data class Api(val errors: List<CustomAPIError>?): Error() {
        val mainError: CustomAPIError?
            get() = errors?.lastOrNull()
        val message: String?
            get() = mainError?.detail
    }
}

data class CustomAPIError(
    val code: Int,
    val status: String,
    val title: String,
    val detail: String
)