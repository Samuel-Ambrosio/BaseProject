package com.samuelav.presentation.common.app.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.samuelav.presentation.common.ui.theme.AppAnimations

@Composable
fun <T : Any> AppNavDisplay(
    modifier: Modifier,
    backStack: List<T>,
    transition: ContentTransform = AppAnimations.fade,
    popTransition: ContentTransform = AppAnimations.popFade,
    screens: EntryProviderScope<T>.() -> Unit,
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        entryProvider = entryProvider(builder = screens),
        transitionSpec = { transition },
        popTransitionSpec = { popTransition },
    )
}