package com.samuelav.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.Heading1
import com.samuelav.commonandroid.ui.composables.Screen
import com.samuelav.features.home.R

@Composable
internal fun MainScreen(appState: AppState) {
    Screen(appState = appState, titleTopBar = stringResource(id = R.string.nav_item_home)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = "Home")
        }
    }
}