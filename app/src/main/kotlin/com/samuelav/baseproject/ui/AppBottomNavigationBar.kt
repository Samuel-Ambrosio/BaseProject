package com.samuelav.baseproject.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.extensions.navigatePoppingUpToStartDestination

@Composable
fun AppBottomNavigationBar(appState: AppState) {
    BottomNavigation {
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        appState.appConfiguration.navItems.forEach { navItem ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = navItem.icon.painter,
                        contentDescription = stringResource(id = navItem.contentDescription))
                },
                label = { Text(text = stringResource(id = navItem.title)) },
                selected = currentRoute?.contains(navItem.route) == true,
                onClick = {
                    appState.navController.navigatePoppingUpToStartDestination(route = navItem.route)
                }
            )
        }
    }
}