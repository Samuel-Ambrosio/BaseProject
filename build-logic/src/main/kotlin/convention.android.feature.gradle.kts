import com.samuelav.buildlogic.libs
import com.samuelav.buildlogic.library

plugins {
    id("convention.android.compose.library")
}

android {
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(project(":domain:model"))
    implementation(project(":domain:useCase"))
    implementation(project(":presentation:analytics"))
    implementation(project(":presentation:common"))

    testImplementation(platform(libs.library("androidx.compose.bom")))
    testImplementation(libs.library("test.android.junit.compose"))
    testImplementation(libs.library("test.android.core"))
    testImplementation(libs.library("test.koin"))
    testImplementation(libs.library("test.robolectric"))
    debugImplementation(libs.library("test.android.ui.compose"))
}
