package com.samuelav.commonandroid.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.configuration.screen.AppBottomNavigationBarScreenConfig
import com.samuelav.commonandroid.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.commonandroid.app.configuration.screen.ScreenConfig

@Composable
fun Screen(
    appState: AppState,
    titleTopBar: String = "",
    isTopBarVisible: Boolean = true,
    isBottomNavigationBarVisible: Boolean = true,
    content: @Composable () -> Unit
) {
    /**
     * LaunchedEffect(true) is not called if screen is changed quickly
     */
    SideEffect {
        appState.screenConfig.value =
            ScreenConfig(
                appTopBarScreenConfig =
                    AppTopBarScreenConfig(title = titleTopBar, isVisible = isTopBarVisible),
                appBottomNavigationBarScreenConfig =
                    AppBottomNavigationBarScreenConfig(isVisible = isBottomNavigationBarVisible)
            )
    }

    content.invoke()
}