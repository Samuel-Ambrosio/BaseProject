package com.samuelav.presentation.analytics.ui

import com.samuelav.presentation.analytics.base.AnalyticsAction

sealed class AdvertScreenAnalytics {
    object Click: AnalyticsAction(eventName = "advert_click")
}