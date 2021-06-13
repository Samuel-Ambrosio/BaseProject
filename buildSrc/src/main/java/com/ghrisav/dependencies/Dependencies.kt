package com.ghrisav.dependencies

class Dependencies {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.core}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Kotlin.coroutines}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.Koin.core}"
        const val android = "io.insert-koin:koin-android:${Versions.Koin.android}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"

        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.lifecycleExtensions}"

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintlayout}"
        const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swiperefreshlayout}"
        const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerview}"

        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}"

        const val preferenceKtx = "androidx.preference:preference-ktx:${Versions.AndroidX.preferenceKtx}"
        const val securityCrypto = "androidx.security:security-crypto:${Versions.AndroidX.securityCrypto}"
    }

    object Room {
        const val coreRuntime = "androidx.room:room-runtime:${Versions.Room.core}"
        const val coreKtx = "androidx.room:room-ktx:${Versions.Room.core}"
        const val compiler = "androidx.room:room-compiler:${Versions.Room.core}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.Retrofit.core}"
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit.core}"
    }

    object OkHttp3 {
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp3.interceptor}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.Google.material}"
        const val gson = "com.google.code.gson:gson:${Versions.Google.gson}"
    }

    object Glide {
        const val core = "com.github.bumptech.glide:glide:${Versions.Glide.core}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.Glide.core}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val androidJunit = "androidx.test.ext:junit:${Versions.Test.androidJunit}"
        const val androidEspresso = "androidx.test.espresso:espresso-core:${Versions.Test.androidEspresso}"
    }
}