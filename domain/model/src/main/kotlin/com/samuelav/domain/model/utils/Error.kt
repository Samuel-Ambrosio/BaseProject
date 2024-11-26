package com.samuelav.domain.model.utils

sealed class Error {
    data object NoContent: Error()
    data object BadRequest: Error()
    data object Forbidden: Error()
    data object NotFound: Error()
    data object Timeout: Error()
    data object Server: Error()
    data object BadGateway: Error()
    data object ServiceUnavailable: Error()

    data object JsonSyntax: Error()
    data object DateTimeParse: Error()
    data object Network: Error()
    data object Unknown: Error()

    data class Api(val errors: List<CustomAPIError>?): Error() {
        val mainError: CustomAPIError?
            get() = errors?.firstOrNull()

        val title: String?
            get() = mainError?.title

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