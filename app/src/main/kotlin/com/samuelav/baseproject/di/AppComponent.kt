package com.samuelav.baseproject.di

import com.samuelav.data.local.di.localModule
import com.samuelav.data.remote.di.remoteModule
import com.samuelav.data.repositoryimpl.di.repositoryModule
import com.samuelav.domain.usecase.di.useCaseModule
import com.samuelav.presentation.analytics.di.analyticsModule
import com.samuelav.presentation.features.home.di.featureHomeModule

val appComponent =
    listOf(
        appModule,
        localModule,
        remoteModule,
        repositoryModule,
        useCaseModule,
        analyticsModule,
        featureHomeModule)