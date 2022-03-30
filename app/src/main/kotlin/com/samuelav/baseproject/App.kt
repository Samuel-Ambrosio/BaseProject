package com.samuelav.baseproject

import android.app.Application
import com.samuelav.baseproject.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

open class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }
}