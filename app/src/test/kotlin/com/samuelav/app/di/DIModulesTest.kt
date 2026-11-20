package com.samuelav.app.di

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.analytics.FirebaseAnalytics
import com.samuelav.app.TestApplication
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.test.check.checkKoinModules
import org.koin.test.mock.MockProviderRule
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DIModulesTest {
    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz -> mockkClass(type = clazz, relaxed = true) }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val sdkAndNetworkOverridesModule = module {
        single<FirebaseAnalytics> { mockk(relaxed = true) }

        single<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain -> emptyTodosResponse(chain) })
                .build()
        }
    }

    @Test
    fun appComponentResolvesEveryDefinition() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        checkKoinModules(
            modules = appComponent + sdkAndNetworkOverridesModule,
            appDeclaration = { androidContext(application) },
        ) {
            withInstance<Context>(application)
        }
    }

    private fun emptyTodosResponse(chain: Interceptor.Chain): Response =
        Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body("[]".toResponseBody("application/json".toMediaType()))
            .build()
}
