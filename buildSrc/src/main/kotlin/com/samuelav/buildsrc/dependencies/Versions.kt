package com.samuelav.buildsrc.dependencies

object Versions {
    object Gradle {
        const val plugin = "7.1.2"
        const val versionsPlugin = "0.42.0"
        const val graphGenerator = "0.7.0"
    }

    object Kotlin {
        const val core = "1.6.10"
        const val coroutines = "1.6.0"
        const val compilerExtension = "1.0.3"
    }

    object Koin {
        const val core = "3.1.5"
        const val android = "3.1.5"
    }

    object AndroidX {
        const val coreKtx = "1.7.0"
        const val appCompat = "1.4.1"

        const val lifecycle = "2.4.1"
        const val lifecycleExtensions = "2.2.0"

        const val compose = "1.1.1"
        const val composeActivity = "1.4.0"
        const val composeNavigation = "2.4.1"
        const val composeConstraint = "1.0.0"

        const val splashScreen = "1.0.0-beta02"
        const val dataStore = "1.0.0"
    }

    object Google {
        const val material = "1.5.0"
        const val gson = "2.9.0"
    }

    object Room {
        const val core = "2.4.2"
    }

    object Retrofit {
        const val core = "2.9.0"
    }

    object OkHttp3 {
        const val interceptor = "4.9.3"
    }

    object Test {
        const val junit = "4.13.2"
        const val androidJunit = "1.1.3"
        const val androidJunitCompose = AndroidX.compose
        const val androidUiTestCompose = AndroidX.compose
        const val androidEspresso = "3.4.0"
    }
}