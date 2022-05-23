package com.samuelav.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.Heading1
import com.samuelav.commonandroid.ui.composables.Screen
import com.samuelav.features.home.R

@Composable
internal fun MainScreen(appState: AppState) {
    Screen(appState = appState, titleTopBar = stringResource(id = R.string.nav_item_home)) {
        Heading1(text = "Home")
    }
}