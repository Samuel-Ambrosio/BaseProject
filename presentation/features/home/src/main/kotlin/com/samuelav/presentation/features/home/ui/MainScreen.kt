package com.samuelav.presentation.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.ui.composables.base.Heading1
import com.samuelav.presentation.common.ui.composables.base.Screen
import com.samuelav.presentation.features.home.R

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