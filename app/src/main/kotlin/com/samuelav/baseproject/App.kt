package com.samuelav.baseproject

import android.app.Application
import com.samuelav.baseproject.di.appComponent
import com.samuelav.baseproject.utils.debug.TimberLogger
import com.samuelav.domain.model.utils.debug.AppLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

open class App: Application() {
    // TODO: Remove '//' after google-services.json file is added
    //private val timberTree: Timber.Tree by lazy {
        //CrashReportingTree(FirebaseCrashlytics.getInstance())
    //}

    override fun onCreate() {
        super.onCreate()

        // TODO: Remove '//' after google-services.json file is added
        //FirebaseApp.initializeApp(this)

        //Timber.plant(timberTree)
        AppLogger.logger = TimberLogger

        startKoin {
            androidContext(this@App)
            modules(appComponent)
        }
    }
}