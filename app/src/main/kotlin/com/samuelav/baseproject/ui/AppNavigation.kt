package com.samuelav.baseproject.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.rememberAppState
import com.samuelav.commonandroid.extensions.isAppBottomNavigationBarVisible
import com.samuelav.commonandroid.extensions.isAppTopBarVisible

@Composable
fun AppNavigation(
    appState: AppState = rememberAppState()
) {
    Scaffold(
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
        content = { innerPadding ->
            AppNavHost(appState = appState, modifier = Modifier.padding(paddingValues = innerPadding))
        }
    )
}