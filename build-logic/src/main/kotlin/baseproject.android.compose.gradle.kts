import com.android.build.api.dsl.CommonExtension
import com.samuelav.buildlogic.libs
import com.samuelav.buildlogic.library

// Compose setup shared by application and library modules. Must be applied after the Android plugin.

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

extensions.configure<CommonExtension> {
    buildFeatures.compose = true
}

dependencies {
    val composeBom = platform(libs.library("androidx.compose.bom"))
    "implementation"(composeBom)
    "implementation"(libs.library("androidx.compose.ui"))
    "implementation"(libs.library("androidx.compose.foundation"))
    "implementation"(libs.library("androidx.compose.material3"))
    "implementation"(libs.library("androidx.compose.constraint"))
    "implementation"(libs.library("androidx.compose.ui.tooling.preview"))
    "debugImplementation"(libs.library("androidx.compose.ui.tooling"))

    "implementation"(libs.library("androidx.lifecycle.viewmodel"))
    "implementation"(libs.library("androidx.lifecycle.compose"))
    "implementation"(libs.library("androidx.lifecycle.viewmodel.navigation3"))
    "implementation"(libs.library("androidx.compose.navigation3.runtime"))
    "implementation"(libs.library("androidx.compose.navigation3.ui"))

    "implementation"(libs.library("koin.compose"))

    "androidTestImplementation"(composeBom)
    "androidTestImplementation"(libs.library("test.android.junit.compose"))
}
