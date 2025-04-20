import com.samuelav.buildsrc.plugins.extensions.android
import com.samuelav.buildsrc.plugins.extensions.androidTestImplementation
import com.samuelav.buildsrc.plugins.extensions.debugImplementation
import com.samuelav.buildsrc.plugins.extensions.implementation
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("KotlinAndroidBase")
    id("org.jetbrains.kotlin.plugin.compose")
}

val libs = the<LibrariesForLibs>()

android {
    buildFeatures.apply { compose = true }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.constraint)

    implementation(libs.koin.compose)

    implementation(libs.google.accompanist.navigation.animation)
    implementation(libs.google.accompanist.permissions)
    implementation(libs.google.accompanist.pager)
    implementation(libs.google.accompanist.swiperefresh)
    implementation(libs.google.accompanist.placeholder)

    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.test.android.junit.compose)
}