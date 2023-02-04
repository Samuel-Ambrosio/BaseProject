package com.samuelav.presentation.common.app.configuration.screen

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppTopBarScreenConfig(
    val isVisible: Boolean,
    val isBackButtonVisible: Boolean = false,
    val title: String = "",
    val onBackClick: (() -> Unit)? = null,
)