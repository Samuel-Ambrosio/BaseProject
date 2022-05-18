package com.samuelav.data.local.di

import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module

val localModule = module {
    single {
        preferencesDataStore(name = "data-store").getValue(thisRef = get(), property = String::javaClass)
    }
}