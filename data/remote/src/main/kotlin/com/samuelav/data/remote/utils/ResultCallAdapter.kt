package com.samuelav.data.remote.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ResultCallAdapter<S : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, Error.Api>,
    private val firebaseCrashlytics: FirebaseCrashlytics
) : CallAdapter<S, Call<Result<Error, S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<Result<Error, S>> =
        ResultResponseCall(call, errorBodyConverter, firebaseCrashlytics)
}