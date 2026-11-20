package com.samuelav.data.sourcesImpl.remote.impl

import com.samuelav.data.sources.TodoRemoteDataSource
import com.samuelav.data.sourcesImpl.remote.di.remoteModule
import com.samuelav.data.sourcesImpl.remote.services.TodoService
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.CustomAPIError
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.SocketEffect
import org.junit.After
import org.junit.Before
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoRemoteDataSourceIntegrationTest {
    private val server = MockWebServer()
    private lateinit var koinApplication: KoinApplication
    private lateinit var sut: TodoRemoteDataSource

    @Before
    fun setUp() {
        server.start()
        koinApplication = koinApplication {
            modules(
                remoteModule,
                module {
                    single<TodoService> {
                        get<Retrofit>().newBuilder().baseUrl(server.url("/")).build().create(TodoService::class.java)
                    }
                },
            )
        }
        sut = koinApplication.koin.get()
    }

    @After
    fun tearDown() {
        koinApplication.close()
        server.close()
    }

    @Test
    fun `requests GET todos and maps the body to domain todos`() = runTest {
        server.enqueue(
            MockResponse.Builder()
                .code(200)
                .body(
                    """[{"userId":1,"id":1,"title":"First","completed":false},{"userId":1,"id":2,"title":"Second","completed":true}]"""
                )
                .build()
        )

        val result = sut.getAll()

        assertEquals(
            Result.Success(
                listOf(
                    Todo(userId = 1, id = 1, title = "First", completed = false),
                    Todo(userId = 1, id = 2, title = "Second", completed = true),
                )
            ),
            result,
        )
        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/todos", request.url.encodedPath)
    }

    @Test
    fun `maps an empty successful response to NoContent`() = runTest {
        server.enqueue(MockResponse.Builder().code(204).build())

        assertEquals(Result.Failure(Error.NoContent), sut.getAll())
    }

    @Test
    fun `maps an error response with api errors to Error Api`() = runTest {
        server.enqueue(
            MockResponse.Builder()
                .code(404)
                .body("""{"errors":[{"code":404,"status":"Not Found","title":"Missing","detail":"No todos"}]}""")
                .build()
        )

        assertEquals(
            Result.Failure(
                Error.Api(
                    errors = listOf(
                        CustomAPIError(code = 404, status = "Not Found", title = "Missing", detail = "No todos")
                    )
                )
            ),
            sut.getAll(),
        )
    }

    @Test
    fun `maps an error response without body to the error of its status code`() = runTest {
        val expectedByCode = mapOf(
            400 to Error.BadRequest,
            403 to Error.Forbidden,
            404 to Error.NotFound,
            408 to Error.Timeout,
            500 to Error.Server,
            502 to Error.BadGateway,
            503 to Error.ServiceUnavailable,
            504 to Error.Server,
            401 to Error.Unknown,
        )

        expectedByCode.forEach { (code, expected) ->
            val attempts = if (code == HTTP_REQUEST_TIMEOUT) OKHTTP_ATTEMPTS_ON_408 else 1
            repeat(attempts) { server.enqueue(MockResponse.Builder().code(code).build()) }

            assertEquals(Result.Failure(expected), sut.getAll(), "HTTP $code")
        }
    }

    @Test
    fun `maps an error response with an empty error list to the error of its status code`() = runTest {
        server.enqueue(MockResponse.Builder().code(503).body("""{"errors":[]}""").build())

        assertEquals(Result.Failure(Error.ServiceUnavailable), sut.getAll())
    }

    @Test
    fun `maps an error response with an unexpected body to the error of its status code`() = runTest {
        server.enqueue(MockResponse.Builder().code(400).body("<html>Bad request</html>").build())

        assertEquals(Result.Failure(Error.BadRequest), sut.getAll())
    }

    @Test
    fun `maps a malformed successful body to JsonSyntax`() = runTest {
        server.enqueue(MockResponse.Builder().code(200).body("""[{"userId":1,"id":1}]""").build())

        assertEquals(Result.Failure(Error.JsonSyntax), sut.getAll())
    }

    @Test
    fun `maps a dropped connection to Network`() = runTest {
        server.enqueue(MockResponse.Builder().onResponseStart(SocketEffect.ShutdownConnection).build())

        assertEquals(Result.Failure(Error.Network), sut.getAll())
    }

    private companion object {
        const val HTTP_REQUEST_TIMEOUT = 408
        const val OKHTTP_ATTEMPTS_ON_408 = 2
    }
}
