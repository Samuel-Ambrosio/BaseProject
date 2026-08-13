package com.samuelav.data.sourcesImpl.local.di

import androidx.datastore.preferences.preferencesDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

public val localModule: Module = module {
    single {
        preferencesDataStore(name = "data-store").getValue(thisRef = get(), property = String::javaClass)
    }
    //single { appRoomDatabaseProvider(get()) }
}

//private fun appRoomDatabaseProvider(context: Context) = AppRoomDatabase.buildDatabase(context)