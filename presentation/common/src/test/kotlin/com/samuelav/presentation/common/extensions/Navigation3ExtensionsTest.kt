package com.samuelav.presentation.common.extensions

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.samuelav.presentation.common.app.navigation.AppNavKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Navigation3ExtensionsTest {
    private val home = AppNavKey.Home.Main
    private val search = AppNavKey.Search.Main
    private val more = AppNavKey.More.Main
    private val detailA = Detail(id = 1)
    private val detailB = Detail(id = 2)

    @Test
    fun `safeAdd ignores the screen already on top`() {
        val backStack = backStackOf(home)

        backStack.safeAdd(home)
        backStack.safeAdd(search)

        assertEquals(listOf(home, search), backStack.toList())
    }

    @Test
    fun `safeRemoveLast pops the top screen`() {
        val backStack = backStackOf(home, search)

        backStack.safeRemoveLast(fallbackScreen = home)

        assertEquals(listOf<NavKey>(home), backStack.toList())
    }

    @Test
    fun `safeRemoveLast replaces the last screen with the fallback instead of emptying the stack`() {
        val backStack = backStackOf(search)

        backStack.safeRemoveLast(fallbackScreen = home)

        assertEquals(listOf<NavKey>(home), backStack.toList())
    }

    @Test
    fun `removeLastOrNavigateUp navigates up only when one screen is left`() {
        var navigatedUp = 0
        val backStack = backStackOf(home, search)

        backStack.removeLastOrNavigateUp { navigatedUp++ }
        backStack.removeLastOrNavigateUp { navigatedUp++ }

        assertEquals(listOf<NavKey>(home), backStack.toList())
        assertEquals(1, navigatedUp)
    }

    @Test
    fun `clearAndAdd replaces the whole stack`() {
        val backStack = backStackOf(home, search, detailA)

        backStack.clearAndAdd(home, more)

        assertEquals(listOf(home, more), backStack.toList())
    }

    @Test
    fun `removeLastAndAdd replaces only the top screen`() {
        val backStack = backStackOf(home, search)

        backStack.removeLastAndAdd(more, detailA)

        assertEquals(listOf(home, more, detailA), backStack.toList())
    }

    @Test
    fun `removeLastInstancesOf pops every consecutive instance of the type`() {
        val backStack = backStackOf(home, detailA, detailB)

        backStack.removeLastInstancesOf<Detail>(fallbackScreen = search)

        assertEquals(listOf<NavKey>(home), backStack.toList())
    }

    @Test
    fun `removeLastInstancesOf adds the fallback when the stack ends up empty`() {
        val backStack = backStackOf(detailA, detailB)

        backStack.removeLastInstancesOf<Detail>(fallbackScreen = home)

        assertEquals(listOf<NavKey>(home), backStack.toList())
    }

    @Test
    fun `removeUntilInstanceAndReplaceIt pops down to the type and replaces it`() {
        val backStack = backStackOf(home, detailA, search, more)

        backStack.removeUntilInstanceAndReplaceIt(newInstance = detailB, fallbackScreen = home)

        assertEquals(listOf(home, detailB), backStack.toList())
    }

    @Test
    fun `removeUntilInstanceAndReplaceIt keeps the instance when there is no replacement`() {
        val backStack = backStackOf(home, detailA, search)

        backStack.removeUntilInstanceAndReplaceIt<Detail>(newInstance = null, fallbackScreen = home)

        assertEquals(listOf(home, detailA), backStack.toList())
    }

    @Test
    fun `removeUntilInstanceAndReplaceIt adds the fallback when the type is not in the stack`() {
        val backStack = backStackOf(search, more)

        backStack.removeUntilInstanceAndReplaceIt(newInstance = detailA, fallbackScreen = home)

        assertEquals(listOf<NavKey>(home), backStack.toList())
    }

    @Test
    fun `defaultContentKey distinguishes instances of the same type`() {
        assertEquals(defaultContentKey(Detail(id = 1)), defaultContentKey(Detail(id = 1)))
        assertNotEquals(defaultContentKey(detailA), defaultContentKey(detailB))
    }

    private fun backStackOf(vararg keys: NavKey) = NavBackStack(*keys)

    private data class Detail(val id: Int) : NavKey
}
