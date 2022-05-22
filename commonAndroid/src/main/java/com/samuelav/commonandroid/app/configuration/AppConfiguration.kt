package com.samuelav.commonandroid.app.configuration

import com.samuelav.commonandroid.app.navigation.NavItem

class AppConfiguration {
    val initialTitleTopBar = NavItem.Home.title
    val initialTopBarVisibility = true
    val initialBottomBarVisibility = true
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.Settings)
}