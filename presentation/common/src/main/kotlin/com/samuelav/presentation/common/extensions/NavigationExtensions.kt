package com.samuelav.presentation.common.extensions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.samuelav.presentation.common.app.navigation.NavCommand
import com.samuelav.presentation.common.app.navigation.PopUpOption
import com.samuelav.presentation.common.ui.theme.AppAnimations

fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = AppAnimations.navFadeIn,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = AppAnimations.navFadeOut,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) = enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = navCommand.route,
        arguments = navCommand.args + navCommand.optionalArgs,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}

fun NavHostController.navigate(route: String, popUpOption: PopUpOption = PopUpOption.NONE) =
    when (popUpOption) {
        PopUpOption.NONE -> navigate(route = route)
        PopUpOption.CURRENT -> navigatePoppingUpToTop(route = route)
        PopUpOption.TO_HOME_MAIN -> navigatePoppingUpToHomeMain(route = route)
    }

fun NavHostController.navigatePoppingUpToStartDestination(route: String) =
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigatePoppingUpToTop(route: String) =
    currentBackStackEntry?.destination?.route?.let { currentBackStackRoute ->
        navigate(route) { popUpTo(currentBackStackRoute) { inclusive = true } }
    }

private fun NavHostController.navigatePoppingUpToHomeMain(route: String) =
    navigate(route) { popUpTo(NavCommand.HomeSection.Main.route) { inclusive = false } }