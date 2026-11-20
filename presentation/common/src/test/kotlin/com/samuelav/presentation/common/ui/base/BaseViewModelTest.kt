package com.samuelav.presentation.common.ui.base

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state starts with the initial state`() {
        val sut = CounterViewModel()

        assertEquals(0, sut.state.value)
    }

    @Test
    fun `emitState replaces and updateState transforms the state`() {
        val sut = CounterViewModel()

        sut.set(5)
        sut.increment()

        assertEquals(6, sut.state.value)
    }

    @Test
    fun `work launched in ioCoroutineScope runs on the injected dispatcher`() = runTest(dispatcher) {
        val sut = CounterViewModel()

        sut.incrementInBackground()
        advanceUntilIdle()

        assertEquals(1, sut.state.value)
    }

    @Test
    fun `emitCommand delivers one-shot commands to the collector`() = runTest(dispatcher) {
        val sut = CounterViewModel()

        sut.command.test {
            sut.notify("first")
            sut.notify("second")

            assertEquals("first", awaitItem())
            assertEquals("second", awaitItem())
        }
    }

    private inner class CounterViewModel : BaseViewModel<Int, String>(initialState = 0, ioDispatcher = dispatcher) {
        fun set(value: Int) = emitState(value)

        fun increment() = updateState { it + 1 }

        fun incrementInBackground() {
            ioCoroutineScope.launch { updateState { it + 1 } }
        }

        fun notify(message: String) = emitCommand(message)
    }
}
