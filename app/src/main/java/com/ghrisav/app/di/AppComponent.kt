package com.ghrisav.app.di

import com.ghrisav.data.api.di.apiModule
import com.ghrisav.data.local.di.localModule
import com.ghrisav.data.repository.di.repositoryModule
import com.ghrisav.features.home.di.featureHomeModule
import com.ghrisav.features.login.di.featureLoginModule
import org.koin.core.module.Module

val appComponent = listOf(
    appModule,
    apiModule,
    localModule,
    repositoryModule,
    featureLoginModule,
    featureHomeModule
)