package com.samuelav.app.di

import com.samuelav.presentation.common.app.configuration.AppConfiguration
import org.koin.dsl.module

val appModule = module {
    single { AppConfiguration() }
}
