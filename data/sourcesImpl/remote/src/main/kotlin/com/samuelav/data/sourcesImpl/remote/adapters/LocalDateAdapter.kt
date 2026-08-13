package com.samuelav.data.sourcesImpl.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

internal class LocalDateAdapter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @ToJson
    fun toJson(value: LocalDate): String = formatter.format(value)

    @FromJson
    fun fromJson(value: String): LocalDate =
        try {
            LocalDate.parse(value, formatter)
        } catch (e: DateTimeParseException) {
            throw JsonDataException("Failed to parse LocalDate: $value", e)
        }
}
