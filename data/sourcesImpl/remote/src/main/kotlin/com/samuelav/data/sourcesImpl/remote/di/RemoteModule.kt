package com.samuelav.data.sourcesImpl.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.samuelav.data.sources.TodoRemoteDataSource
import com.samuelav.data.sourcesImpl.remote.BuildConfig
import com.samuelav.data.sourcesImpl.remote.BuildConfig.API_BASE_PATH
import com.samuelav.data.sourcesImpl.remote.impl.TodoRemoteDataSourceImpl
import com.samuelav.data.sourcesImpl.remote.services.TodoService
import com.samuelav.data.sourcesImpl.remote.utils.ResultCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

val remoteModule = module {
    single { interceptorProvider() }
    single { okHttpClientProvider(interceptor = get()) }
    single { gsonProvider() }
    single { ResultCallAdapterFactory() }
    single {
        retrofitProvider(
            okHttpClient = get(),
            gson = get(),
            resultCallAdapterFactory = get(),
        )
    }

    single<TodoService> { get<Retrofit>().create(TodoService::class.java) }

    single<TodoRemoteDataSource> { TodoRemoteDataSourceImpl(todoService = get()) }
}

/**
 *  Retrofit configuration
 */
private fun interceptorProvider(): Interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

private fun okHttpClientProvider(interceptor: Interceptor) =
    OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) { addInterceptor(interceptor) }
    }.build()

private fun gsonProvider() =
    GsonBuilder()
        .registerTypeAdapter(
            OffsetDateTime::class.java,
            object : JsonSerializer<OffsetDateTime>, JsonDeserializer<OffsetDateTime> {
                private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

                override fun serialize(
                    src: OffsetDateTime,
                    typeOfSrc: Type,
                    context: JsonSerializationContext
                ): JsonElement = JsonPrimitive(formatter.format(src))

                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): OffsetDateTime {
                    val jsonString = json?.asString
                    return try {
                        OffsetDateTime.parse(jsonString, formatter)
                    } catch (e: DateTimeParseException) {
                        throw JsonParseException("Failed to parse OffsetDateTime: $jsonString", e)
                    }
                }
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