package com.samuelav.common.utils

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
    object Network: Error()
    object Unknown: Error()
}