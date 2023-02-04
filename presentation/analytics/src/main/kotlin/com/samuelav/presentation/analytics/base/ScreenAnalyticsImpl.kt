package com.samuelav.presentation.analytics.base

internal class ScreenAnalyticsImpl(
    private val coreFirebaseAnalytics: CoreFirebaseAnalytics
): ScreenAnalytics {
    override fun notifyAction(action: AnalyticsAction?) {
        action ?: return
        coreFirebaseAnalytics.logEvent(action.eventName) {
            action.parameters.forEach { putString(it.name, it.value) }
        }
    }
}