package com.samuelav.baseproject.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.NavCommand
import com.samuelav.presentation.common.extensions.composable
import com.samuelav.presentation.common.ui.composables.base.Heading1
import com.samuelav.presentation.common.ui.composables.base.Screen
import com.samuelav.presentation.common.ui.theme.AppAnimations
import com.samuelav.presentation.features.home.ui.homeNavigation

@Composable
fun AppNavHost(appState: AppState, modifier: Modifier) {
    NavHost(
        navController = appState.appNavigation.navHostController,
        startDestination = NavCommand.HomeSection.Main.rootRoute,
        modifier = modifier,
    ) {
        homeNavigation(appState = appState)
        searchNavigation(appState = appState)
        moreNavigation(appState = appState)
    }
}

private fun NavGraphBuilder.searchNavigation(appState: AppState) {
    navigation(
        startDestination = NavCommand.SearchSection.Main.route,
        route = NavCommand.SearchSection.Main.rootRoute,
    ) {
        composable(
            navCommand = NavCommand.SearchSection.Main,
            enterTransition = AppAnimations.navSlideInDown,
            exitTransition = AppAnimations.navSlideOutUp,
            popExitTransition = AppAnimations.navSlideOutUp
        ) {
            SearchScreen(appState = appState)
        }
    }
}

private fun NavGraphBuilder.moreNavigation(appState: AppState) {
    navigation(
        startDestination = NavCommand.MoreSection.Main.route,
        route = NavCommand.MoreSection.Main.rootRoute,
    ) {
        composable(
            navCommand = NavCommand.MoreSection.Main,
            enterTransition = AppAnimations.navSlideInLeft,
            exitTransition = AppAnimations.navSlideOutRight,
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
        titleTopBar = stringResource(id = com.samuelav.presentation.common.R.string.nav_item_more),
        isBottomNavigationBarVisible = false,
        isBackButtonVisible = true,
        onBackClick = appState.appNavigation::navigateUp,
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