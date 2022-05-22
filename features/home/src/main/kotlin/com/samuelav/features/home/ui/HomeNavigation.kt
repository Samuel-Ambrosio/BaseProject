package com.samuelav.features.home.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.navigation.Feature

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNavigation(appState: AppState) {
    navigation(
        startDestination = HomeDestination.Main.route,
        route = Feature.Home.rootRoute
    ) {
        composable(route = HomeDestination.Main.route) {
            MainScreen(appState = appState)
        }
    }
}

private sealed class HomeDestination(
    val childRoute: String
) {
    val route = listOf(Feature.Home.rootRoute).plus(childRoute).joinToString("/")

    object Main: HomeDestination(childRoute = "main")
}