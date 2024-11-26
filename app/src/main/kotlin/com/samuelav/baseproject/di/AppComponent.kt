package com.samuelav.baseproject.di

import com.samuelav.data.repositoryimpl.di.repositoryModule
import com.samuelav.data.sourcesImpl.local.di.localModule
import com.samuelav.data.sourcesImpl.remote.di.remoteModule
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