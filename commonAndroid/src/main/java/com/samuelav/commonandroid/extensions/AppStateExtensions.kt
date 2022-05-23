package com.samuelav.commonandroid.extensions

import com.samuelav.commonandroid.app.AppState

fun AppState.isAppTopBarVisible() = screenConfig.value.appTopBarScreenConfig.isVisible

fun AppState.isAppBottomNavigationBarVisible() =
    screenConfig.value.appBottomNavigationBarScreenConfig.isVisible