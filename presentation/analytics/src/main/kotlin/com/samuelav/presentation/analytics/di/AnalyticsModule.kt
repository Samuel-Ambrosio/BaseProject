package com.samuelav.presentation.analytics.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.samuelav.presentation.analytics.base.CoreFirebaseAnalytics
import com.samuelav.presentation.analytics.base.ScreenAnalytics
import com.samuelav.presentation.analytics.base.ScreenAnalyticsImpl
import org.koin.dsl.module

val analyticsModule = module {
    single { FirebaseAnalytics.getInstance(get()) }
    single { CoreFirebaseAnalytics(context = get(), firebaseAnalytics = get()) }
    single<ScreenAnalytics> { ScreenAnalyticsImpl(coreFirebaseAnalytics = get()) }
}