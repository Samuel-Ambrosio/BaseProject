package com.samuelav.presentation.common.extensions

import android.content.Context
import com.samuelav.domain.model.utils.Error
import com.samuelav.presentation.common.R

fun Error.handleErrorMessage(context: Context): String =
    when (this) {
        is Error.NoContent -> context.getString(R.string.result_error_no_content)
        is Error.BadRequest -> context.getString(R.string.result_error_bad_request)
        is Error.Forbidden -> context.getString(R.string.result_error_forbidden)
        is Error.NotFound -> context.getString(R.string.result_error_not_found)
        is Error.Timeout -> context.getString(R.string.result_error_timeout)
        is Error.Server -> context.getString(R.string.result_error_server)
        is Error.BadGateway -> context.getString(R.string.result_error_bad_gateway)
        is Error.ServiceUnavailable -> context.getString(R.string.result_error_service_unavailable)
        is Error.JsonSyntax -> context.getString(R.string.result_error_unknown)
        is Error.DateTimeParse -> context.getString(R.string.result_error_unknown)
        is Error.Network -> context.getString(R.string.result_error_network)
        is Error.Unknown -> context.getString(R.string.result_error_unknown)
        is Error.Api -> title ?: context.getString(R.string.result_error_unknown)
    }