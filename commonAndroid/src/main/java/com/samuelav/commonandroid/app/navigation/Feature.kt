package com.samuelav.commonandroid.app.navigation

sealed class Feature(val rootRoute: String) {
    object Home: Feature(rootRoute = "home")
}