package com.samuelav.commonandroid.extensions

import androidx.annotation.StringRes
import com.samuelav.common.Error
import com.samuelav.commonandroid.R

@StringRes
fun Error.handleErrorMessage() =
    when (this) {
        is Error.NoContent -> R.string.result_error_no_content
        is Error.BadRequest -> R.string.result_error_bad_request
        is Error.Forbidden -> R.string.result_error_forbidden
        is Error.NotFound -> R.string.result_error_not_found
        is Error.Timeout -> R.string.result_error_timeout
        is Error.Server -> R.string.result_error_server
        is Error.BadGateway -> R.string.result_error_bad_gateway
        is Error.ServiceUnavailable -> R.string.result_error_service_unavailable
        is Error.Network -> R.string.result_error_network
        is Error.Unknown -> R.string.result_error_unknown
    }