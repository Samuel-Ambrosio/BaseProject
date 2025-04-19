package com.samuelav.data.sourcesImpl.remote.models

import com.samuelav.domain.model.models.Todo


internal data class NetworkTodo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean,
)

internal fun NetworkTodo.toTodo() =
    Todo(
        userId = userId,
        id = id,
        title = title,
        completed = completed,
    )