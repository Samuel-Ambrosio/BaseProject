package com.samuelav.baseproject.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.NavItem
import com.samuelav.presentation.common.extensions.clearAndAdd
import com.samuelav.presentation.common.ui.theme.AppTheme.colors

@Composable
internal fun AppBottomNavigationBar(appState: AppState) {
    val navItems = appState.appConfiguration.navItems
    val lastNavItemKey =
        appState.navBackStack.lastOrNull { navKey ->
            navKey.javaClass in navItems.map { it.navKey.javaClass }
        }
    val currentNavItem = navItems.firstOrNull { navItem ->
        navItem.navKey.javaClass == lastNavItemKey?.javaClass
    } ?: NavItem.Home

    BottomNavigation(backgroundColor = colors.surface, contentColor = colors.primary) {
        navItems.forEach { navItem ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = navItem.icon.painter,
                        contentDescription = stringResource(id = navItem.contentDescription),
                    )
                },
                selected = navItem == currentNavItem,
                onClick = {
                    appState.navBackStack.clearAndAdd(NavItem.Home.navKey, navItem.navKey)
                }
            )
        }
    }
}