package com.samuelav.presentation.common.app.navigation

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.samuelav.presentation.common.R
import com.samuelav.presentation.common.ui.theme.AppIconography
import com.samuelav.presentation.common.ui.theme.Icon

sealed class NavItem(
    val navKey: NavKey,
    val icon: Icon,
    @StringRes val title: Int,
    @StringRes val contentDescription: Int,
) {
    data object Home: NavItem(
        navKey = AppNavKey.Home.Main,
        icon = AppIconography.home,
        title = R.string.nav_item_home,
        contentDescription = R.string.nav_item_home,
    )
    data object Search: NavItem(
        navKey = AppNavKey.Search.Main,
        icon = AppIconography.search,
        title = R.string.nav_item_search,
        contentDescription = R.string.nav_item_search,
    )
    data object More: NavItem(
        navKey = AppNavKey.More.Main,
        icon = AppIconography.more,
        title = R.string.nav_item_more,
        contentDescription = R.string.nav_item_more,
    )
}