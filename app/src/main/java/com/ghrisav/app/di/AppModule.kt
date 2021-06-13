package com.ghrisav.app.di

import com.ghrisav.app.domain.GetTokenUseCase
import com.ghrisav.app.ui.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GetTokenUseCase(get()) }
    viewModel { SplashViewModel(get()) }
}