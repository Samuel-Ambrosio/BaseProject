package com.samuelav.baseproject.di

import com.samuelav.baseproject.ui.activity.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel() }
}