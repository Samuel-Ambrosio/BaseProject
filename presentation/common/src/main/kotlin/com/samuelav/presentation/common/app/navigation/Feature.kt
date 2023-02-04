package com.samuelav.presentation.common.app.navigation

sealed class Feature(val rootRoute: String) {
    object Home: Feature(rootRoute = "home")
}