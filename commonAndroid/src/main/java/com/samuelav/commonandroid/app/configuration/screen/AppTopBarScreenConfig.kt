package com.samuelav.commonandroid.app.configuration.screen

import androidx.annotation.StringRes

data class AppTopBarScreenConfig(
    @StringRes val title: Int,
    val isVisible: Boolean
)