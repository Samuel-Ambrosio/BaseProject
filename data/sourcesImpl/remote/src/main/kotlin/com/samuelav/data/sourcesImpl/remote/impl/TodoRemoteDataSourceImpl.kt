package com.samuelav.data.sourcesImpl.remote.impl

import com.samuelav.data.sources.TodoRemoteDataSource
import com.samuelav.data.sourcesImpl.remote.models.toTodo
import com.samuelav.data.sourcesImpl.remote.services.TodoService
import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.model.utils.mapSuccess

internal class TodoRemoteDataSourceImpl(
    private val todoService: TodoService,
) : TodoRemoteDataSource {

    override suspend fun getAll(): Result<Error, List<Todo>> =
        todoService.getTodos().mapSuccess { it.map { networkTodo -> networkTodo.toTodo() } }
}