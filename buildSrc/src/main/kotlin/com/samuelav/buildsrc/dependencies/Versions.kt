package com.samuelav.buildsrc.dependencies

object Versions {
    object Gradle {
        const val plugin = "8.1.1"
        const val versionsPlugin = "0.47.0"
        const val graphGenerator = "0.8.0"
        const val crashlytics = "2.9.9"
    }

    object Kotlin {
        const val core = "1.9.0"
        const val coroutines = "1.7.3"
    }

    object Koin {
        const val core = "3.4.3"
        const val compose = "3.4.6"
    }

    object Android {
        const val desugarJdkLibs = "2.0.3"
    }

    object AndroidX {
        const val coreKtx = "1.10.1"
        const val appCompat = "1.6.1"

        const val lifecycle = "2.6.1"

        const val compose = "2023.08.00"
        const val composeCompiler = "1.5.2"
        const val composeNavigation = "2.5.3"
        const val composeConstraint = "1.0.1"

        const val splashScreen = "1.0.1"

        const val room = "2.5.2"
        const val dataStore = "1.0.0"
    }

    object Google {
        const val accompanist = "0.32.0"
        const val material = "1.9.0"
        const val gson = "2.10.1"
        const val services = "4.3.15"
        const val servicesAds = "22.3.0"
        const val firebase = "32.2.3"
    }

    object Retrofit {
        const val core = "2.9.0"
    }

    object OkHttp3 {
        const val interceptor = "4.11.0"
    }

    object Test {
        const val junit = "4.13.2"
        const val androidJunit = "1.1.5"
        const val androidEspresso = "3.5.1"
    }
}