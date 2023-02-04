package com.samuelav.baseproject.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.samuelav.baseproject.R
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.NavItem
import com.samuelav.presentation.common.ui.composables.base.Heading1
import com.samuelav.presentation.common.ui.composables.base.Screen
import com.samuelav.presentation.common.ui.theme.AppAnimations
import com.samuelav.presentation.features.home.ui.homeNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(appState: AppState, modifier: Modifier) {
    AnimatedNavHost(
        navController = appState.navController,
        startDestination = NavItem.Home.route,
        modifier = modifier) {
        homeNavigation(appState = appState)

        composable(
            route = NavItem.Search.route,
            enterTransition = AppAnimations.navSlideInDown,
            exitTransition = AppAnimations.navSlideOutUp,
            popExitTransition = AppAnimations.navSlideOutUp
        ) {
            SearchScreen(appState = appState)
        }

        composable(
            route = NavItem.More.route,
            enterTransition = AppAnimations.navSlideInLeft,
            exitTransition = AppAnimations.navSlideOutRight
        ) {
            MoreScreen(appState = appState)
        }
    }
}

@Composable
private fun SearchScreen(appState: AppState) {
    Screen(appState = appState, isTopBarVisible = false) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = "Search")
        }
    }
}

@Composable
private fun MoreScreen(appState: AppState) {
    Screen(
        appState = appState,
        titleTopBar = stringResource(id = R.string.nav_item_more),
        isBottomNavigationBarVisible = false,
        isBackButtonVisible = true,
        onBackClick = { appState.navController.navigateUp() },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = "More")
        }
    }
}