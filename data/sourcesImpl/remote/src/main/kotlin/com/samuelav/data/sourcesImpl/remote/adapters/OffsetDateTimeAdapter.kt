package com.samuelav.data.sourcesImpl.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

internal class OffsetDateTimeAdapter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @ToJson
    fun toJson(value: OffsetDateTime): String = formatter.format(value)

    @FromJson
    fun fromJson(value: String): OffsetDateTime =
        try {
            OffsetDateTime.parse(value, formatter)
        } catch (e: DateTimeParseException) {
            throw JsonDataException("Failed to parse OffsetDateTime: $value", e)
        }
}
