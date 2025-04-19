package com.samuelav.domain.usecase.todo

import com.samuelav.domain.repository.todo.TodoRepository

class GetAllTodosUseCase(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke() = todoRepository.getAll()
}