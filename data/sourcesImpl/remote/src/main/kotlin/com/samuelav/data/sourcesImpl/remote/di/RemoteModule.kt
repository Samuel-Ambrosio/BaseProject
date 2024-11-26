package com.samuelav.data.sourcesImpl.remote.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.*
import com.samuelav.data.sourcesImpl.remote.BuildConfig
import com.samuelav.data.sourcesImpl.remote.BuildConfig.API_BASE_PATH
import com.samuelav.data.sourcesImpl.remote.utils.ResultCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZonedDateTime

val remoteModule = module {
    single { interceptorProvider() }
    single { okHttpClientProvider(get()) }
    single { gsonProvider() }
    single { FirebaseCrashlytics.getInstance() }
    single { ResultCallAdapterFactory(get()) }
    single { retrofitProvider(get(), get(), get()) }
}

/**
 *  Retrofit configuration
 */
private fun interceptorProvider(): Interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun okHttpClientProvider(interceptor: Interceptor) =
    OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) { addInterceptor(interceptor) }
        build()
    }

private fun gsonProvider() =
    GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .registerTypeAdapter(
            LocalDateTime::class.java,
            object : JsonDeserializer<LocalDateTime> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ) = ZonedDateTime.parse(json?.asJsonPrimitive?.asString).toLocalDateTime()
            }
        ).create()

private fun retrofitProvider(
    okHttpClient: OkHttpClient,
    gson: Gson,
    resultCallAdapterFactory: ResultCallAdapterFactory
) =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_BASE_PATH)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(resultCallAdapterFactory)
        .build()