package com.samuelav.commonandroid.extensions

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.configuration.screen.AppBottomNavigationBarScreenConfig
import com.samuelav.commonandroid.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.commonandroid.app.configuration.screen.ScreenConfig

@Composable
fun AppState.setupScreenConfig(
    @StringRes titleTopBar: Int = appConfiguration.initialTitleTopBar,
    isTopBarVisible: Boolean = appConfiguration.initialTopBarVisibility,
    isBottomNavigationBarVisible: Boolean = appConfiguration.initialBottomBarVisibility
) = apply {
    LaunchedEffect(key1 = null) {
        screenConfig.value =
            ScreenConfig(
                appTopBarScreenConfig =
                    AppTopBarScreenConfig(title = titleTopBar, isVisible = isTopBarVisible),
                appBottomNavigationBarScreenConfig =
                    AppBottomNavigationBarScreenConfig(isVisible = isBottomNavigationBarVisible))
    }
}

fun AppState.isAppTopBarVisible() = screenConfig.value.appTopBarScreenConfig.isVisible

fun AppState.isAppBottomNavigationBarVisible() =
    screenConfig.value.appBottomNavigationBarScreenConfig.isVisible