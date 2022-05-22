package com.samuelav.features.home.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.setupScreenConfig
import com.samuelav.features.home.R

@Composable
internal fun MainScreen(appState: AppState) {
    appState.setupScreenConfig(titleTopBar = R.string.nav_item_home)
    Text(text = "Home")
}