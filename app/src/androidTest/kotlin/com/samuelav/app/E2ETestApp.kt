package com.samuelav.app

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class E2ETestApp : App() {
    override fun onCreate() {
        super.onCreate()
        loadKoinModules(
            module {
                single<OkHttpClient> {
                    OkHttpClient.Builder()
                        .addInterceptor(Interceptor { chain -> FakeBackend.respond(chain) })
                        .build()
                }
            }
        )
    }
}

object FakeBackend {
    @Volatile
    var todosResponse: FakeResponse = FakeResponse(code = 200, body = "[]")

    fun respond(chain: Interceptor.Chain): Response {
        val fake = todosResponse
        return Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(fake.code)
            .message("Fake")
            .body(fake.body.toResponseBody("application/json".toMediaType()))
            .build()
    }

    data class FakeResponse(val code: Int, val body: String)
}
