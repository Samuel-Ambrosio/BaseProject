package com.samuelav.data.repositoryimpl.di

import com.samuelav.data.repositoryimpl.todo.TodoRepositoryImpl
import com.samuelav.domain.repository.todo.TodoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TodoRepository> { TodoRepositoryImpl(todoRemoteDataSource = get()) }
}