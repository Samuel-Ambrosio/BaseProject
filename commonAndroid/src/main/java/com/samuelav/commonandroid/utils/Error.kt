package com.samuelav.commonandroid.utils

import androidx.annotation.StringRes
import com.samuelav.commonandroid.R

sealed class Error(@StringRes val messageResId: Int) {
    object NoContent: Error(messageResId = R.string.result_error_no_content)
    object BadRequest: Error(messageResId = R.string.result_error_bad_request)
    object Forbidden: Error(messageResId = R.string.result_error_forbidden)
    object NotFound: Error(messageResId = R.string.result_error_not_found)
    object Timeout: Error(messageResId = R.string.result_error_timeout)
    object Server: Error(messageResId = R.string.result_error_server)
    object BadGateway: Error(messageResId = R.string.result_error_bad_gateway)
    object ServiceUnavailable: Error(messageResId = R.string.result_error_service_unavailable)

    object Network: Error(messageResId = R.string.result_error_network)
    object Unknown: Error(messageResId = R.string.result_error_unknown)
}