package com.samuelav.baseproject.di

import com.samuelav.data.local.di.localModule
import com.samuelav.data.remote.di.remoteModule
import com.samuelav.data.repository.di.repositoryModule
import com.samuelav.domain.di.domainModule
import com.samuelav.features.home.homeFeatureModule

val appComponent =
    listOf(appModule, localModule, remoteModule, repositoryModule, domainModule, homeFeatureModule)