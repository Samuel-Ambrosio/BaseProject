package com.samuelav.presentation.common.extensions

import androidx.annotation.StringRes
import com.samuelav.domain.model.utils.Error
import com.samuelav.presentation.common.R

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
        is Error.JsonSyntax -> R.string.result_error_unknown
        is Error.DateTimeParse -> R.string.result_error_unknown
        is Error.Network -> R.string.result_error_network
        is Error.Unknown -> R.string.result_error_unknown
        is Error.Api -> R.string.result_error_unknown
    }