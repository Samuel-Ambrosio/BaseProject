package com.samuelav.domain.repository.todo

import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result

interface TodoRepository {
    suspend fun getAll(): Result<Error, List<Todo>>
}