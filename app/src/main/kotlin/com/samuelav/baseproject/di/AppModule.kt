package com.samuelav.baseproject.di

import com.samuelav.baseproject.ui.activity.SplashViewModel
import com.samuelav.commonandroid.app.configuration.AppConfiguration
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppConfiguration() }
    viewModel { SplashViewModel() }
}