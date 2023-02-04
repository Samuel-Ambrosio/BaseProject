package com.samuelav.presentation.common.app.navigation

sealed class Feature(val route: String) {
    data object Main: Feature(route = "main")
}