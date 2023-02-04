package com.samuelav.presentation.common.app.navigation

import androidx.annotation.StringRes
import com.samuelav.presentation.common.R
import com.samuelav.presentation.common.ui.theme.AppIconography
import com.samuelav.presentation.common.ui.theme.Icon

sealed class NavItem(
    val route: String,
    val icon: Icon,
    @StringRes val title: Int,
    @StringRes val contentDescription: Int,
) {
    object Home: NavItem(route = "home", icon = AppIconography.home, title = R.string.nav_item_home, contentDescription = R.string.nav_item_home)
    object Search: NavItem(route = "search", icon = AppIconography.search, title = R.string.nav_item_search, contentDescription = R.string.nav_item_search)
    object More: NavItem(route = "more", icon = AppIconography.more, title = R.string.nav_item_more, contentDescription = R.string.nav_item_more)
}