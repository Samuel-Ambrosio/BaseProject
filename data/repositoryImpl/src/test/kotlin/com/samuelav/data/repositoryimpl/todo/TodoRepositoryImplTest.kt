package com.samuelav.data.repositoryimpl.todo

import com.samuelav.data.sources.TodoRemoteDataSource
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.models.fakeTodos
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoRepositoryImplTest {
    @Test
    fun `getAll returns the todos of the remote data source`() = runTest {
        val dataSource = FakeTodoRemoteDataSource(Result.Success(fakeTodos))
        val sut = TodoRepositoryImpl(todoRemoteDataSource = dataSource)

        assertEquals(Result.Success(fakeTodos), sut.getAll())
        assertEquals(1, dataSource.calls)
    }

    @Test
    fun `getAll propagates the remote data source failure`() = runTest {
        val sut = TodoRepositoryImpl(todoRemoteDataSource = FakeTodoRemoteDataSource(Result.Failure(Error.Server)))

        assertEquals(Result.Failure(Error.Server), sut.getAll())
    }

    private class FakeTodoRemoteDataSource(private val result: Result<Error, List<Todo>>) : TodoRemoteDataSource {
        var calls = 0
            private set

        override suspend fun getAll(): Result<Error, List<Todo>> {
            calls++
            return result
        }
    }
}
