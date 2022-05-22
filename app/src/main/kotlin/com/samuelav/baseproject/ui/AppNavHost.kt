package com.samuelav.baseproject.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.samuelav.baseproject.R
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.navigation.NavItem
import com.samuelav.commonandroid.extensions.setupScreenConfig
import com.samuelav.features.home.ui.homeNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(appState: AppState, modifier: Modifier) {
    AnimatedNavHost(
        navController = appState.navController,
        startDestination = NavItem.Home.route,
        modifier = modifier) {
        homeNavigation(appState = appState)

        composable(route = NavItem.Search.route) {
            SearchScreen(appState = appState)
        }
        composable(route = NavItem.Settings.route) {
            SettingsScreen(appState = appState)
        }
    }
}

@Composable
private fun SearchScreen(appState: AppState) {
    appState.setupScreenConfig(isTopBarVisible = false)
    Text(text = "Search")
}

@Composable
private fun SettingsScreen(appState: AppState) {
    appState.setupScreenConfig(titleTopBar = R.string.nav_item_settings, isBottomNavigationBarVisible = false)
    Text(text = "Settings")
}