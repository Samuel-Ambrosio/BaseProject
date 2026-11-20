package com.samuelav.domain.usecase.todo

import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.models.fakeTodos
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.repository.todo.TodoRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllTodosUseCaseTest {
    @Test
    fun `returns the todos of the repository`() = runTest {
        val sut = GetAllTodosUseCase(todoRepository = FakeTodoRepository(Result.Success(fakeTodos)))

        assertEquals(Result.Success(fakeTodos), sut())
    }

    @Test
    fun `propagates the repository failure`() = runTest {
        val sut = GetAllTodosUseCase(todoRepository = FakeTodoRepository(Result.Failure(Error.Network)))

        assertEquals(Result.Failure(Error.Network), sut())
    }

    private class FakeTodoRepository(private val result: Result<Error, List<Todo>>) : TodoRepository {
        override suspend fun getAll(): Result<Error, List<Todo>> = result
    }
}
