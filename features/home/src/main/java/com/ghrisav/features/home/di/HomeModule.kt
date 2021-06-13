package com.ghrisav.features.home.di

import com.ghrisav.features.home.domain.GetAlbumsUseCase
import com.ghrisav.features.home.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    single { GetAlbumsUseCase(get()) }
    viewModel { HomeViewModel(get()) }
}