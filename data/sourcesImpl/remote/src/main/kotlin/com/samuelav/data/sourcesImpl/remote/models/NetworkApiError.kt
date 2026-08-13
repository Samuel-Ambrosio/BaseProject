package com.samuelav.data.sourcesImpl.remote.models

import com.samuelav.domain.model.utils.CustomAPIError
import com.samuelav.domain.model.utils.Error
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class NetworkApiError(
    val errors: List<NetworkCustomApiError>?,
)

@JsonClass(generateAdapter = true)
internal data class NetworkCustomApiError(
    val code: Int,
    val status: String,
    val title: String,
    val detail: String,
)

internal fun NetworkApiError.toError() = Error.Api(errors = errors?.map { it.toCustomAPIError() })

internal fun NetworkCustomApiError.toCustomAPIError() =
    CustomAPIError(
        code = code,
        status = status,
        title = title,
        detail = detail,
    )
