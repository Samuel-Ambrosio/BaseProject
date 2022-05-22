package com.samuelav.baseproject.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.samuelav.commonandroid.app.AppState

@Composable
fun AppTopBar(appState: AppState) {
    val screenConfig by appState.screenConfig

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = screenConfig.appTopBarScreenConfig.title))
        }
    )
}