package com.samuelav.presentation.features.home.di

import com.samuelav.presentation.features.home.ui.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    viewModel {
        MainViewModel(getAllTodosUseCase = get())
    }
}