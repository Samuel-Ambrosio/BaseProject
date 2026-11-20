package com.samuelav.presentation.common.ui.base

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
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
class ColdStateBaseViewModelTest {
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
    fun `does not load until the state is collected`() = runTest(dispatcher) {
        val sut = LoaderViewModel()
        advanceUntilIdle()

        assertEquals(0, sut.loads)
        assertEquals(LoadState.Idle, sut.state.value)
    }

    @Test
    fun `loads when the state is collected`() = runTest(dispatcher) {
        val sut = LoaderViewModel()

        backgroundScope.launch { sut.state.collect() }
        advanceUntilIdle()

        assertEquals(1, sut.loads)
        assertEquals(LoadState.Ready(load = 1), sut.state.value)
    }

    @Test
    fun `retry runs onInitState again and goes through loading`() = runTest(dispatcher) {
        val sut = LoaderViewModel()

        sut.state.test {
            assertEquals(LoadState.Idle, awaitItem())
            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Ready(load = 1), awaitItem())

            sut.retry()

            assertEquals(LoadState.Loading, awaitItem())
            assertEquals(LoadState.Ready(load = 2), awaitItem())
        }
    }

    @Test
    fun `emitCommand delivers one-shot commands to the collector`() = runTest(dispatcher) {
        val sut = LoaderViewModel()

        sut.command.test {
            sut.notify("done")

            assertEquals("done", awaitItem())
        }
    }

    private inner class LoaderViewModel :
        ColdStateBaseViewModel<LoadState, String>(initialState = LoadState.Idle, ioDispatcher = dispatcher) {
        var loads = 0
            private set

        override suspend fun FlowCollector<LoadState>.onInitState() {
            emit(LoadState.Loading)
            loads++
            emit(LoadState.Ready(load = loads))
        }

        fun retry() = retryInitState()

        fun notify(message: String) = emitCommand(message)
    }

    private sealed interface LoadState {
        data object Idle : LoadState

        data object Loading : LoadState

        data class Ready(val load: Int) : LoadState
    }
}
