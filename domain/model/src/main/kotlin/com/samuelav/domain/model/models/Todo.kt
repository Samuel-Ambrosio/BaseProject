package com.samuelav.domain.model.models

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean,
)

val fakeTodo = Todo(userId = 1, id = 1, title = "Write the first test", completed = false)

val fakeTodos = listOf(fakeTodo, fakeTodo.copy(id = 2, title = "Make it pass", completed = true))
