package com.ghrisav.data.api.di

import com.ghrisav.data.api.BuildConfig.API_BASE_PATH
import com.ghrisav.data.api.mock.PhotoApiDataSourceImpl
import com.ghrisav.data.api.mock.PhotoWs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ghrisav.data.api.session.SessionApiDataSourceImpl
import com.ghrisav.data.api.session.SessionWs
import com.ghrisav.data.source.mock.PhotoApiDataSource
import com.ghrisav.data.source.session.SessionApiDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { interceptorProvider() }
    single { okHttpClientProvider(get()) }
    single { gsonProvider() }
    single { retrofitProvider(get(), get()) }
    single { sessionWsProvider(get()) }
    single { photoWsProvider(get()) }
    single<SessionApiDataSource> { SessionApiDataSourceImpl(get()) }
    single<PhotoApiDataSource> { PhotoApiDataSourceImpl(get()) }
}

/* Retrofit configuration */
private fun interceptorProvider(): Interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun okHttpClientProvider(interceptor: Interceptor) =
    OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

private fun gsonProvider() =
    GsonBuilder()
        .setDateFormat("dd/MM/yyyy hh:mm")
        .create()

private fun retrofitProvider(okHttpClient: OkHttpClient, gson: Gson) =
    Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(API_BASE_PATH)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


/* Service providers */

private fun sessionWsProvider(retrofit: Retrofit) = retrofit.create(SessionWs::class.java)

private fun photoWsProvider(retrofit: Retrofit) = retrofit.create(PhotoWs::class.java)