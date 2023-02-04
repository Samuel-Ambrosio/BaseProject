package com.samuelav.baseproject.di

import com.samuelav.presentation.common.app.configuration.AppConfiguration
import org.koin.dsl.module

val appModule = module {
    single { AppConfiguration() }
}