package com.samuelav.commonandroid.app.configuration

import com.samuelav.common.app.AppCommonConfiguration
import com.samuelav.commonandroid.app.navigation.NavItem

class AppConfiguration: AppCommonConfiguration() {
    val initialTopBarVisibility = true
    val initialBottomBarVisibility = true
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.Settings)
}