package com.samuelav.baseproject.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.AppNavDisplay
import com.samuelav.presentation.common.app.navigation.AppNavKey
import com.samuelav.presentation.common.extensions.appEntry
import com.samuelav.presentation.common.extensions.safeRemoveLast
import com.samuelav.presentation.common.ui.composables.base.Heading1
import com.samuelav.presentation.common.ui.composables.base.Screen
import com.samuelav.presentation.common.ui.theme.AppAnimations
import com.samuelav.presentation.features.home.ui.homeNavEntries
import kotlin.collections.plus

@Composable
internal fun AppNavHost(appState: AppState, modifier: Modifier) {
    AppNavDisplay(
        modifier = modifier,
        backStack = appState.navBackStack,
    ) {
        homeNavEntries(appState = appState)

        searchNavEntries()

        moreNavEntries(
            navigateUp = {
                appState.navBackStack.safeRemoveLast(fallbackScreen = AppNavKey.Home.Main)
            },
        )
    }
}

private fun EntryProviderScope<NavKey>.searchNavEntries() {
    appEntry<AppNavKey.Search.Main>(
        metadata = {
            NavDisplay.transitionSpec { AppAnimations.slideVertically } +
            NavDisplay.popTransitionSpec { AppAnimations.popSlideVertically }
        }
    ) {
        SearchScreen()
    }
}

private fun EntryProviderScope<NavKey>.moreNavEntries(
    navigateUp: () -> Unit,
) {
    appEntry<AppNavKey.More.Main>(
        metadata = {
            NavDisplay.transitionSpec { AppAnimations.slideHorizontally } +
            NavDisplay.popTransitionSpec { AppAnimations.popSlideHorizontally }
        }
    ) {
        MoreScreen(navigateUp = navigateUp)
    }
}

@Composable
private fun SearchScreen() {
    Screen(isTopBarVisible = false) {
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
private fun MoreScreen(
    navigateUp: () -> Unit,
) {
    Screen(
        titleTopBar = stringResource(id = com.samuelav.presentation.common.R.string.nav_item_more),
        isBackButtonVisible = true,
        onBackClick = navigateUp,
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
