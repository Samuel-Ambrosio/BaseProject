package com.samuelav.baseproject.utils.analytics

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.samuelav.domain.model.extensions.isNotNull
import kotlinx.coroutines.CancellationException
import timber.log.Timber

class CrashReportingTree(private val firebaseCrashlytics: FirebaseCrashlytics) : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        firebaseCrashlytics.log(message)

        if (priority == Log.ERROR && t !is CancellationException && t.isNotNull()) {
            firebaseCrashlytics.recordException(t)
        }
    }
}