package com.samuelav.presentation.common.app.configuration

import com.samuelav.presentation.common.app.navigation.NavItem

class AppConfiguration {
    val initialTopBarVisibility = true
    val initialBottomBarVisibility = true
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.More)
}