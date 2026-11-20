package com.samuelav.presentation.features.home.ui

import app.cash.turbine.test
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.models.fakeTodos
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.usecase.todo.GetAllTodosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MainViewModelTest {
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
    fun `starts loading`() {
        val sut = mainViewModel(Result.Success(fakeTodos))

        assertEquals(MainState.Loading, sut.state.value)
    }

    @Test
    fun `shows the todos once they are loaded`() = runTest(dispatcher) {
        val sut = mainViewModel(Result.Success(fakeTodos))

        backgroundScope.launch { sut.state.collect() }
        advanceUntilIdle()

        assertEquals(MainState.Ready(todos = fakeTodos), sut.state.value)
    }

    @Test
    fun `shows the failure when loading fails`() = runTest(dispatcher) {
        val sut = mainViewModel(Result.Failure(Error.Network))

        backgroundScope.launch { sut.state.collect() }
        advanceUntilIdle()

        assertEquals(MainState.Failure(error = Error.Network), sut.state.value)
    }

    @Test
    fun `retry loads the todos again after a failure`() = runTest(dispatcher) {
        val sut = mainViewModel(Result.Failure(Error.Network), Result.Success(fakeTodos))

        sut.state.test {
            assertEquals(MainState.Loading, awaitItem())
            assertEquals(MainState.Failure(error = Error.Network), awaitItem())

            sut.retry()

            assertEquals(MainState.Loading, awaitItem())
            assertEquals(MainState.Ready(todos = fakeTodos), awaitItem())
        }
    }

    private fun mainViewModel(vararg results: Result<Error, List<Todo>>) =
        MainViewModel(
            getAllTodosUseCase = GetAllTodosUseCase(todoRepository = FakeTodoRepository(*results)),
            ioDispatcher = dispatcher,
        )
}
