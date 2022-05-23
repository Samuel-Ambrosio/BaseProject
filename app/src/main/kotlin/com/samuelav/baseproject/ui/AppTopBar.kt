package com.samuelav.baseproject.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.Heading2

@Composable
fun AppTopBar(appState: AppState) {
    val screenConfig by appState.screenConfig

    TopAppBar(
        title = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Heading2(text = screenConfig.appTopBarScreenConfig.title)
            }
        }
    )
}