package com.samuelav.baseproject.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.samuelav.baseproject.R
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.app.navigation.NavItem
import com.samuelav.commonandroid.ui.composables.base.Heading1
import com.samuelav.commonandroid.ui.composables.base.Screen
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
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
private fun SettingsScreen(appState: AppState) {
    Screen(
        appState = appState,
        titleTopBar = stringResource(id = R.string.nav_item_settings),
        isBottomNavigationBarVisible = false,
        onBackClick = { appState.navController.navigateUp() },
        topBarActions = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = icons.menu.painter,
                        tint = colors.onSurface,
                        contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = icons.close.painter,
                        tint = colors.onSurface,
                        contentDescription = null)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Heading1(text = "Settings")
        }
    }
}