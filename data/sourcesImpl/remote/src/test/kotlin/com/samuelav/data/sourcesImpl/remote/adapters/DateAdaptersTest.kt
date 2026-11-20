package com.samuelav.data.sourcesImpl.remote.adapters

import com.squareup.moshi.JsonDataException
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DateAdaptersTest {
    private val localDateAdapter = LocalDateAdapter()
    private val offsetDateTimeAdapter = OffsetDateTimeAdapter()

    @Test
    fun `LocalDate round-trips through ISO local date`() {
        val date = LocalDate.of(2026, 11, 20)

        assertEquals("2026-11-20", localDateAdapter.toJson(date))
        assertEquals(date, localDateAdapter.fromJson("2026-11-20"))
    }

    @Test
    fun `LocalDate rejects a malformed value with JsonDataException`() {
        assertFailsWith<JsonDataException> { localDateAdapter.fromJson("20/11/2026") }
    }

    @Test
    fun `OffsetDateTime round-trips through ISO offset date time`() {
        val dateTime = OffsetDateTime.of(2026, 11, 20, 10, 30, 0, 0, ZoneOffset.ofHours(1))

        assertEquals("2026-11-20T10:30:00+01:00", offsetDateTimeAdapter.toJson(dateTime))
        assertEquals(dateTime, offsetDateTimeAdapter.fromJson("2026-11-20T10:30:00+01:00"))
    }

    @Test
    fun `OffsetDateTime rejects a value without offset with JsonDataException`() {
        assertFailsWith<JsonDataException> { offsetDateTimeAdapter.fromJson("2026-11-20T10:30:00") }
    }
}
