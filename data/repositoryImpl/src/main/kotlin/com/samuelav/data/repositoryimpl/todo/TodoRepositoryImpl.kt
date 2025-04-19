package com.samuelav.data.repositoryimpl.todo

import com.samuelav.data.sources.TodoRemoteDataSource
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.repository.todo.TodoRepository

internal class TodoRepositoryImpl(
    private val todoRemoteDataSource: TodoRemoteDataSource,
) : TodoRepository {
    override suspend fun getAll(): Result<Error, List<Todo>> =
        todoRemoteDataSource.getAll()
}