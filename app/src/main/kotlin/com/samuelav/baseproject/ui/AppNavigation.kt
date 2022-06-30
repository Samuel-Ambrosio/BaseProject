package com.samuelav.baseproject.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.rememberAppState
import com.samuelav.commonandroid.extensions.isAppBottomNavigationBarVisible
import com.samuelav.commonandroid.extensions.isAppTopBarVisible
import com.samuelav.commonandroid.ui.theme.AppTheme.colors

@Composable
fun AppNavigation(
    appState: AppState = rememberAppState()
) {
    Scaffold(
        scaffoldState = appState.scaffoldState,
        topBar = {
            AnimatedVisibility(
                visible = appState.isAppTopBarVisible(),
                enter = slideInVertically(animationSpec = tween()) { -it },
                exit = slideOutVertically(animationSpec = tween()) { -it }) {
                AppTopBar(appState = appState)
            }
         },
        bottomBar = {
            AnimatedVisibility(
                visible = appState.isAppBottomNavigationBarVisible(),
                enter = slideInVertically(animationSpec = tween()) { it },
                exit = slideOutVertically(animationSpec = tween()) { it }) {
                AppBottomNavigationBar(appState = appState)
            }
        },
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = shapes.medium,
                    backgroundColor = colors.primary
                )
            }
        },
        content = { innerPadding ->
            AppNavHost(appState = appState, modifier = Modifier.padding(paddingValues = innerPadding))
        }
    )
}