package com.samuelav.domain.model.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ErrorTest {
    private val firstError = CustomAPIError(code = 404, status = "Not Found", title = "Missing", detail = "No todo")
    private val secondError = CustomAPIError(code = 500, status = "Server", title = "Broken", detail = "Retry")

    @Test
    fun `api error exposes the first error as main error`() {
        val error = Error.Api(errors = listOf(firstError, secondError))

        assertEquals(firstError, error.mainError)
        assertEquals("Missing", error.title)
        assertEquals("No todo", error.message)
    }

    @Test
    fun `api error without errors has no main error, title or message`() {
        listOf(Error.Api(errors = null), Error.Api(errors = emptyList())).forEach { error ->
            assertNull(error.mainError)
            assertNull(error.title)
            assertNull(error.message)
        }
    }
}
