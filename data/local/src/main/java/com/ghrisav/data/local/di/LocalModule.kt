package com.ghrisav.data.local.di

import com.ghrisav.data.local.session.PreferenceUtils
import com.ghrisav.data.local.session.SessionLocalDataSourceImpl
import com.ghrisav.data.source.session.SessionLocalDataSource
import org.koin.dsl.module

val localModule = module {
    single { PreferenceUtils(get()) }
    single<SessionLocalDataSource> { SessionLocalDataSourceImpl(get()) }
    //single { appRoomDatabaseProvider(get()) }
}

//private fun appRoomDatabaseProvider(context: Context) = AppRoomDatabase.buildDatabase(context)

