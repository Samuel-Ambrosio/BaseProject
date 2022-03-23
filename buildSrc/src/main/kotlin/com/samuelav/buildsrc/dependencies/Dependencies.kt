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

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"

        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.lifecycleExtensions}"

        const val composeUi = "androidx.compose.ui:ui:${Versions.AndroidX.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.AndroidX.compose}"
        const val debugComposeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.AndroidX.compose}"
        const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.AndroidX.compose}"
        const val composeIconsCore = "androidx.compose.material:material-icons-core:${Versions.AndroidX.compose}"
        const val composeIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.AndroidX.compose}"
        const val composeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.AndroidX.compose}"

        const val composeActivity = "androidx.activity:activity-compose:${Versions.AndroidX.composeActivity}"
        const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.AndroidX.composeNavigation}"
        const val composeConstraint = "androidx.constraintlayout:constraintlayout-compose:${Versions.AndroidX.composeConstraint}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.Test.androidJunit}"
        const val androidJunitCompose = "androidx.compose.ui:ui-test-junit4:${Versions.Test.androidJunitCompose}"
        const val androidUiTestCompose = "androidx.compose.ui:ui-test-manifest:${Versions.Test.androidUiTestCompose}"
        const val androidEspresso = "androidx.test.espresso:espresso-core:${Versions.Test.androidEspresso}"
    }
}