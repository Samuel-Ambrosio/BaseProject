package com.samuelav.commonandroid.ui.composables.base

import androidx.compose.foundation.layout.RowScope
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
    titleTopBarContent: (@Composable RowScope.() -> Unit)? = null,
    isTopBarVisible: Boolean = true,
    isBottomNavigationBarVisible: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    topBarActions: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    /**
     * LaunchedEffect(true) is not called if screen is changed quickly
     */
    SideEffect {
        appState.screenConfig.value =
            ScreenConfig(
                appTopBarScreenConfig =
                    AppTopBarScreenConfig(
                        title = titleTopBar,
                        titleContent = titleTopBarContent,
                        isVisible = isTopBarVisible,
                        onBackClick = onBackClick,
                        actions = topBarActions),
                appBottomNavigationBarScreenConfig =
                    AppBottomNavigationBarScreenConfig(
                        isVisible = isBottomNavigationBarVisible)
            )
    }

    content.invoke()
}