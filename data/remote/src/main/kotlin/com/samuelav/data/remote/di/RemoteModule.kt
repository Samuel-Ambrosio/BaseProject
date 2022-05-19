package com.samuelav.data.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.samuelav.data.remote.BuildConfig
import com.samuelav.data.remote.BuildConfig.API_BASE_PATH
import com.samuelav.data.remote.utils.ResultCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single { interceptorProvider() }
    single { okHttpClientProvider(get()) }
    single { gsonProvider() }
    single { retrofitProvider(get(), get()) }
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
        .setDateFormat("dd/MM/yyyy hh:mm")
        .create()

private fun retrofitProvider(okHttpClient: OkHttpClient, gson: Gson) =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_BASE_PATH)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .build()