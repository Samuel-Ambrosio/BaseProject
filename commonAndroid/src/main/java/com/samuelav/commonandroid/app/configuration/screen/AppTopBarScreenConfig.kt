package com.samuelav.commonandroid.app.configuration.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppTopBarScreenConfig(
    val title: String = "",
    val titleContent: (@Composable RowScope.() -> Unit)? = null,
    val isVisible: Boolean,
    val onBackClick: (() -> Unit)? = null,
    val actions: (@Composable RowScope.() -> Unit)? = null,
)