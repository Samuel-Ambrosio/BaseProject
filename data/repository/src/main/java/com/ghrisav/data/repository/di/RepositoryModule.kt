package com.ghrisav.data.repository.di

import com.ghrisav.data.repository.mock.PhotoRepository
import com.ghrisav.data.repository.mock.PhotoRepositoryImpl
import com.ghrisav.data.repository.session.SessionRepository
import com.ghrisav.data.repository.session.SessionRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<SessionRepository> { SessionRepositoryImpl(get(), get()) }
    single<PhotoRepository> { PhotoRepositoryImpl(get(), get(), get()) }
}