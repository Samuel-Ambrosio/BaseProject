package com.samuelav.presentation.common.extensions

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.samuelav.presentation.common.ui.theme.AppTheme

fun ComponentActivity.setAppThemeContent(content: @Composable () -> Unit) {
    setContent { AppTheme { content() } }
}