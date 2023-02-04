package com.samuelav.presentation.common.ui.composables.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.configuration.screen.AppBottomNavigationBarScreenConfig
import com.samuelav.presentation.common.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.presentation.common.app.configuration.screen.ScreenConfig
import kotlinx.coroutines.launch

@Composable
fun Screen(
    appState: AppState,
    isTopBarVisible: Boolean = true,
    isBottomNavigationBarVisible: Boolean = true,
    isBackButtonVisible: Boolean = false,
    titleTopBar: String = "",
    onBackClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    SideEffect {
        appState.appScope.launch {
            appState.screenConfig.emit(
                ScreenConfig(
                    appTopBarScreenConfig =
                        AppTopBarScreenConfig(
                            isVisible = isTopBarVisible,
                            isBackButtonVisible = isBackButtonVisible,
                            title = titleTopBar,
                            onBackClick = onBackClick),
                    appBottomNavigationBarScreenConfig =
                        AppBottomNavigationBarScreenConfig(
                            isVisible = isBottomNavigationBarVisible)))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content.invoke()
    }
}