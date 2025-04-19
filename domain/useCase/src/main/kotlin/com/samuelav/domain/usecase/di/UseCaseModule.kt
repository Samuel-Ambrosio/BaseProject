package com.samuelav.domain.usecase.di

import com.samuelav.domain.usecase.todo.GetAllTodosUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetAllTodosUseCase(todoRepository = get()) }
}