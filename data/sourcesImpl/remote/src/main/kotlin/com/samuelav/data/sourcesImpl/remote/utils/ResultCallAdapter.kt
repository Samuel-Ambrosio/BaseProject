package com.samuelav.data.sourcesImpl.remote.utils

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
) : CallAdapter<S, Call<Result<Error, S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<Result<Error, S>> =
        ResultResponseCall(call, errorBodyConverter)
}