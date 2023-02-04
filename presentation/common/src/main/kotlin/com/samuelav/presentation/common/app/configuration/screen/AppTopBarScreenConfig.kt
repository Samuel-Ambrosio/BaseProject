package com.samuelav.presentation.common.app.configuration.screen

data class AppTopBarScreenConfig(
    val isVisible: Boolean,
    val isBackButtonVisible: Boolean = false,
    val title: String = "",
    val onBackClick: (() -> Unit)? = null,
)