package com.samuelav.data.sourcesImpl.remote.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        check(returnType is ParameterizedType) {
            "$returnType must be parameterized. Raw types are not supported"
        }

        val containerType = getParameterUpperBound(0, returnType)
        if (getRawType(containerType) != Result::class.java) return null

        check(containerType is ParameterizedType) {
            "$containerType must be parameterized. Raw types are not supported"
        }

        val successBodyType = containerType.getBodyType()
        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<Error.Api>(null, Error.Api::class.java, annotations)

        return when (getRawType(returnType)) {
            Call::class.java -> ResultCallAdapter<Any>(successBodyType, errorBodyConverter, firebaseCrashlytics)
            else -> null
        }
    }

    private fun ParameterizedType.getBodyType(): Type = getParameterUpperBound(1, this)
}
