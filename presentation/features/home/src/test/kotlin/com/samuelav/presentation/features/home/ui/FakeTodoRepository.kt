package com.samuelav.presentation.features.home.ui

import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result
import com.samuelav.domain.repository.todo.TodoRepository

internal class FakeTodoRepository(vararg results: Result<Error, List<Todo>>) : TodoRepository {
    private val pendingResults = ArrayDeque(results.toList())

    override suspend fun getAll(): Result<Error, List<Todo>> =
        if (pendingResults.size > 1) pendingResults.removeFirst() else pendingResults.first()
}
