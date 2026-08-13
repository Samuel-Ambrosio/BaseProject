package com.samuelav.presentation.common.extensions

import android.content.res.Resources
import com.samuelav.domain.model.utils.Error
import com.samuelav.presentation.common.R

fun Error.handleErrorMessage(resources: Resources): String =
    when (this) {
        is Error.NoContent -> resources.getString(R.string.result_error_no_content)
        is Error.BadRequest -> resources.getString(R.string.result_error_bad_request)
        is Error.Forbidden -> resources.getString(R.string.result_error_forbidden)
        is Error.NotFound -> resources.getString(R.string.result_error_not_found)
        is Error.Timeout -> resources.getString(R.string.result_error_timeout)
        is Error.Server -> resources.getString(R.string.result_error_server)
        is Error.BadGateway -> resources.getString(R.string.result_error_bad_gateway)
        is Error.ServiceUnavailable -> resources.getString(R.string.result_error_service_unavailable)
        is Error.JsonSyntax -> resources.getString(R.string.result_error_unknown)
        is Error.DateTimeParse -> resources.getString(R.string.result_error_unknown)
        is Error.Network -> resources.getString(R.string.result_error_network)
        is Error.Unknown -> resources.getString(R.string.result_error_unknown)
        is Error.Api -> title ?: resources.getString(R.string.result_error_unknown)
    }