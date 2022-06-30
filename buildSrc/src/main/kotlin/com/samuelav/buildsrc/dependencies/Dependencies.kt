package com.samuelav.buildsrc.dependencies

sealed class Dependencies {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.core}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.Koin.core}"
        const val android = "io.insert-koin:koin-android:${Versions.Koin.android}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.Koin.android}"
    }

    object Android {
        const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:${Versions.Android.desugarJdkLibs}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"

        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.lifecycleExtensions}"

        const val composeUi = "androidx.compose.ui:ui:${Versions.AndroidX.compose}"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.AndroidX.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.AndroidX.compose}"
        const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.AndroidX.compose}"
        const val composeIconsCore = "androidx.compose.material:material-icons-core:${Versions.AndroidX.compose}"
        const val composeIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.AndroidX.compose}"
        const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.AndroidX.compose}"

        const val composeActivity = "androidx.activity:activity-compose:${Versions.AndroidX.composeActivity}"
        const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.AndroidX.composeNavigation}"
        const val composeConstraint = "androidx.constraintlayout:constraintlayout-compose:${Versions.AndroidX.composeConstraint}"

        const val splashScreen = "androidx.core:core-splashscreen:${Versions.AndroidX.splashScreen}"

        const val roomCoreRuntime = "androidx.room:room-runtime:${Versions.AndroidX.room}"
        const val roomCoreKtx = "androidx.room:room-ktx:${Versions.AndroidX.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.AndroidX.room}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Versions.AndroidX.dataStore}"
    }

    object Google {
        const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.Google.accompanist}"
        const val accompanistPermissions = "com.google.accompanist:accompanist-permissions:${Versions.Google.accompanist}"
        const val accompanistPager = "com.google.accompanist:accompanist-pager:${Versions.Google.accompanist}"
        const val accompanistSwipeToRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.Google.accompanist}"
        const val accompanistPlaceholder = "com.google.accompanist:accompanist-placeholder:${Versions.Google.accompanist}"

        const val material = "com.google.android.material:material:${Versions.Google.material}"
        const val gson = "com.google.code.gson:gson:${Versions.Google.gson}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.Retrofit.core}"
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit.core}"
    }

    object OkHttp3 {
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp3.interceptor}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.Test.androidJunit}"
        const val androidJunitCompose = "androidx.compose.ui:ui-test-junit4:${Versions.Test.androidJunitCompose}"
        const val androidUiTestCompose = "androidx.compose.ui:ui-test-manifest:${Versions.Test.androidUiTestCompose}"
        const val androidEspresso = "androidx.test.espresso:espresso-core:${Versions.Test.androidEspresso}"
    }
}