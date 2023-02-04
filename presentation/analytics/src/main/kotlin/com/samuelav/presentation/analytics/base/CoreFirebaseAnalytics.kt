package com.samuelav.presentation.analytics.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.*

internal class CoreFirebaseAnalytics(
    private val context: Context,
    private val firebaseAnalytics: FirebaseAnalytics
) {
    fun logEvent(event: String, paramsBlock: Bundle.() -> Unit = {}) =
        firebaseAnalytics.logEvent(event.lowercase(Locale.ROOT), paramsBlock)

    @OptIn(DelicateCoroutinesApi::class)
    private fun FirebaseAnalytics.logEvent(eventName: String, paramsBlock: Bundle.() -> Unit = {}) {
        GlobalScope.launch { logEvent(eventName, buildBaseBundle(paramsBlock)) }
    }

    private fun buildBaseBundle(block: Bundle.() -> Unit = {}) =
        Bundle().apply {
            putLong(EVENT_PARAM_DATE, Instant.now().toEpochMilli())
            putInt(EVENT_PARAM_DEVICE_OS_VERSION, Build.VERSION.SDK_INT)
            ContextCompat.getSystemService(context, TelephonyManager::class.java)?.also { tm ->
                putString(EVENT_PARAM_DEVICE_CARRIER_NAME, tm.simOperatorName)
            }
            putString(EVENT_PARAM_DEVICE_LANGUAGE, Locale.getDefault().toLanguageTag())
            putString(EVENT_PARAM_DEVICE_TIME_ZONE, TimeZone.getDefault().id)
            block()
        }

    companion object {
        private const val EVENT_PARAM_DATE = "timestamp"
        private const val EVENT_PARAM_DEVICE_OS_VERSION = "device_os_version"
        private const val EVENT_PARAM_DEVICE_CARRIER_NAME = "device_carrier_name"
        private const val EVENT_PARAM_DEVICE_LANGUAGE = "device_language"
        private const val EVENT_PARAM_DEVICE_TIME_ZONE = "device_time_zone"
    }
}