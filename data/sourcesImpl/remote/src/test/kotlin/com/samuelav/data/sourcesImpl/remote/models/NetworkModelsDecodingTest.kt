package com.samuelav.data.sourcesImpl.remote.models

import com.samuelav.data.sourcesImpl.remote.di.remoteModule
import com.samuelav.domain.model.utils.CustomAPIError
import com.samuelav.domain.model.utils.Error
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.After
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NetworkModelsDecodingTest {
    private val koinApplication: KoinApplication = koinApplication { modules(remoteModule) }
    private val moshi: Moshi = koinApplication.koin.get()

    @After
    fun tearDown() {
        koinApplication.close()
    }

    @Test
    fun `decodes a todo and maps it to the domain model`() {
        val json = """{"userId":3,"id":7,"title":"Buy milk","completed":true}"""

        val networkTodo = moshi.adapter(NetworkTodo::class.java).fromJson(json)

        assertEquals(NetworkTodo(userId = 3, id = 7, title = "Buy milk", completed = true), networkTodo)
        assertEquals(7, networkTodo?.toTodo()?.id)
        assertEquals("Buy milk", networkTodo?.toTodo()?.title)
    }

    @Test
    fun `fails to decode a todo without a required field`() {
        val json = """{"userId":3,"id":7,"completed":true}"""

        assertFailsWith<JsonDataException> { moshi.adapter(NetworkTodo::class.java).fromJson(json) }
    }

    @Test
    fun `decodes an api error and maps it to Error Api`() {
        val json = """{"errors":[{"code":404,"status":"Not Found","title":"Missing","detail":"No todo"}]}"""

        val error = moshi.adapter(NetworkApiError::class.java).fromJson(json)?.toError()

        assertEquals(
            Error.Api(
                errors = listOf(CustomAPIError(code = 404, status = "Not Found", title = "Missing", detail = "No todo"))
            ),
            error,
        )
    }

    @Test
    fun `decodes an api error without errors`() {
        val error = moshi.adapter(NetworkApiError::class.java).fromJson("{}")?.toError()

        assertEquals(Error.Api(errors = null), error)
    }
}
