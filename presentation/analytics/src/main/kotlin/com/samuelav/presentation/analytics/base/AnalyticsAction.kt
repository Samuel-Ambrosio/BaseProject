package com.samuelav.presentation.analytics.base

abstract class AnalyticsAction(
    val eventName: String,
    open val parameters: List<AnalyticsEventParameter> = emptyList()
)