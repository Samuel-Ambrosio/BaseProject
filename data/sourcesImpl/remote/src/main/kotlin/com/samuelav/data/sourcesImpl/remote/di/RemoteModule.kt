package com.samuelav.data.sourcesImpl.remote.di

import com.samuelav.data.sources.TodoRemoteDataSource
import com.samuelav.data.sourcesImpl.remote.BuildConfig
import com.samuelav.data.sourcesImpl.remote.BuildConfig.API_BASE_PATH
import com.samuelav.data.sourcesImpl.remote.adapters.LocalDateAdapter
import com.samuelav.data.sourcesImpl.remote.adapters.OffsetDateTimeAdapter
import com.samuelav.data.sourcesImpl.remote.impl.TodoRemoteDataSourceImpl
import com.samuelav.data.sourcesImpl.remote.services.TodoService
import com.samuelav.data.sourcesImpl.remote.utils.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

public val remoteModule: Module = module {
    single { interceptorProvider() }
    single { okHttpClientProvider(interceptor = get()) }
    single { moshiProvider() }
    single { ResultCallAdapterFactory() }
    single {
        retrofitProvider(
            okHttpClient = get(),
            moshi = get(),
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

private fun moshiProvider(): Moshi =
    Moshi.Builder()
        .add(OffsetDateTimeAdapter())
        .add(LocalDateAdapter())
        .build()

private fun retrofitProvider(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
    resultCallAdapterFactory: ResultCallAdapterFactory
) =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_BASE_PATH)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(resultCallAdapterFactory)
        .build()