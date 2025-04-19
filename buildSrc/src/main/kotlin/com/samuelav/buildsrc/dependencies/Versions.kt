package com.samuelav.buildsrc.dependencies

object Versions {
    object Gradle {
        const val plugin = "8.9.1"
        const val versionsPlugin = "0.52.0"
        const val graphGenerator = "0.8.0"
        const val crashlytics = "3.0.3"
    }

    object Kotlin {
        const val core = "2.1.20"
        const val coroutines = "1.10.2"
    }

    object Koin {
        const val core = "4.0.4"
    }

    object Android {
        const val desugarJdkLibs = "2.1.5"
    }

    object AndroidX {
        const val coreKtx = "1.16.0"
        const val appCompat = "1.7.0"

        const val lifecycle = "2.8.7"

        const val compose = "2025.04.00"
        const val composeCompiler = "1.5.15"
        const val composeNavigation = "2.8.9"
        const val composeConstraint = "1.1.1"

        const val splashScreen = "1.0.1"

        const val room = "2.7.0"
        const val dataStore = "1.1.4"
    }

    object Debug {
        const val timber = "5.0.1"
    }

    object Google {
        const val accompanist = "0.36.0"
        const val material = "1.12.0"
        const val gson = "2.13.0"
        const val services = "4.4.2"
        const val servicesAds = "24.2.0"
        const val firebase = "33.12.0"
    }

    object Retrofit {
        const val core = "2.11.0"
    }

    object OkHttp3 {
        const val interceptor = "4.12.0"
    }

    object Test {
        const val junit = "4.13.2"
        const val androidJunit = "1.2.1"
        const val androidEspresso = "3.6.1"
    }
}