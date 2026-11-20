package com.samuelav.baseproject.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.rememberAppState
import com.samuelav.presentation.common.ui.theme.AppTheme.animations
import com.samuelav.presentation.common.ui.theme.AppTheme.colors
import com.samuelav.presentation.features.advert.AdvertBanner

@Composable
internal fun AppScaffold(
    modifier: Modifier,
    appState: AppState = rememberAppState(),
) {
    val isBottomBarVisible =
        appState.navBackStack.lastOrNull()?.javaClass in
            appState.appConfiguration.navItems.map { it.navKey.javaClass }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = animations.bottomBarVisibilityEnter,
                exit = animations.bottomBarVisibilityExit,
            ) {
                AppBottomNavigationBar(appState = appState)
            }
        },
        snackbarHost = {
            SnackbarHost(appState.snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = shapes.medium,
                    containerColor = colors.primary,
                )
            }
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
                AppNavHost(
                    modifier = Modifier.weight(1f),
                    appState = appState,
                )

                AdvertBanner(modifier = Modifier.fillMaxWidth())
            }
        }
    )
}
