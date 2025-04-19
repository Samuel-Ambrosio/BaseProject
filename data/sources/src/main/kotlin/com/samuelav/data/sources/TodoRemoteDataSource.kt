package com.samuelav.data.sources

import com.samuelav.domain.model.models.Todo
import com.samuelav.domain.model.utils.Error
import com.samuelav.domain.model.utils.Result

interface TodoRemoteDataSource {
    suspend fun getAll(): Result<Error, List<Todo>>
}