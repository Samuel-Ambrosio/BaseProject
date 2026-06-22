package com.samuelav.presentation.common.extensions

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.samuelav.domain.model.extensions.isNotNull

inline fun <reified K : NavKey> EntryProviderScope<NavKey>.appEntry(
    noinline clazzContentKey: (key: @JvmSuppressWildcards K) -> Any = { defaultContentKey(it) },
    noinline metadata: (K) -> Map<String, Any> = { emptyMap() },
    noinline content: @Composable (K) -> Unit,
) {
    addEntryProvider(K::class, clazzContentKey, metadata, content)
}

fun NavBackStack<NavKey>.safeAdd(screen: NavKey) {
    if (lastOrNull() != screen) add(screen)
}

fun NavBackStack<NavKey>.safeRemoveLast(fallbackScreen: NavKey) {
    if (size == 1) {
        removeLastOrNull()
        add(fallbackScreen)
    } else {
        removeLastOrNull()
    }
}

inline fun NavBackStack<NavKey>.removeLastOrNavigateUp(navigateUp: () -> Unit) {
    if (size == 1) navigateUp() else removeLastOrNull()
}

fun NavBackStack<NavKey>.clearAndAdd(
    screen: NavKey,
    vararg screens: NavKey,
) {
    clear()
    addAll(arrayOf(screen, *screens))
}

fun NavBackStack<NavKey>.removeLastAndAdd(
    screen: NavKey,
    vararg screens: NavKey,
) {
    removeLastOrNull()
    addAll(arrayOf(screen, *screens))
}

inline fun <reified T : NavKey> NavBackStack<NavKey>.removeLastInstancesOf(fallbackScreen: NavKey) {
    while (lastOrNull() is T) removeLastOrNull()
    if (isEmpty()) add(fallbackScreen)
}

inline fun <reified T : NavKey> NavBackStack<NavKey>.removeUntilInstanceAndReplaceIt(
    newInstance: T?,
    fallbackScreen: NavKey,
) {
    while (lastOrNull().isNotNull() && lastOrNull() !is T) removeLastOrNull()

    when {
        lastOrNull() is T && newInstance.isNotNull() -> {
            removeLastOrNull()
            add(newInstance)
        }
        isEmpty() -> {
            add(fallbackScreen)
        }
    }
}

fun defaultContentKey(key: Any): Any = with(key) { "$javaClass-${toString()}" }
