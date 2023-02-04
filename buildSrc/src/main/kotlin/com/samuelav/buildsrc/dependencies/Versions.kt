package com.samuelav.buildsrc.dependencies

object Versions {
    object Gradle {
        const val plugin = "7.3.0"
        const val versionsPlugin = "0.45.0"
        const val graphGenerator = "0.8.0"
        const val crashlytics = "2.9.2"
    }

    object Kotlin {
        const val core = "1.8.0"
        const val coroutines = "1.6.4"
    }

    object Koin {
        const val core = "3.3.2"
        const val compose = "3.4.1"
    }

    object Android {
        const val desugarJdkLibs = "1.2.2"
    }

    object AndroidX {
        const val coreKtx = "1.9.0"
        const val appCompat = "1.6.0"

        const val lifecycle = "2.6.0-alpha05"

        const val compose = "2022.12.00"
        const val composeCompiler = "1.4.0"
        const val composeNavigation = "2.5.3"
        const val composeConstraint = "1.0.1"

        const val splashScreen = "1.0.0"

        const val room = "2.5.0"
        const val dataStore = "1.0.0"
    }

    object Google {
        const val accompanist = "0.28.0"
        const val material = "1.8.0"
        const val gson = "2.10.1"
        const val services = "4.3.15"
        const val servicesAds = "21.0.0"
        const val firebase = "31.2.0"
    }

    object Retrofit {
        const val core = "2.9.0"
    }

    object OkHttp3 {
        const val interceptor = "4.10.0"
    }

    object Test {
        const val junit = "4.13.2"
        const val androidJunit = "1.1.5"
        const val androidEspresso = "3.5.1"
    }
}