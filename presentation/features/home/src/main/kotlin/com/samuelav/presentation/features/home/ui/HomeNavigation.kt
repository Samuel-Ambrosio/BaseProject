package com.samuelav.presentation.features.home.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.NavCommand
import com.samuelav.presentation.common.extensions.composable
import com.samuelav.presentation.common.ui.theme.AppAnimations

fun NavGraphBuilder.homeNavigation(appState: AppState) {
    navigation(
        startDestination = NavCommand.HomeSection.Main.route,
        route = NavCommand.HomeSection.Main.rootRoute,
    ) {
        composable(
            navCommand = NavCommand.HomeSection.Main,
            popEnterTransition = AppAnimations.navSlideInRight,
            exitTransition = AppAnimations.navSlideOutLeft,
        ) {
            MainScreen(appState = appState)
        }
    }
}
