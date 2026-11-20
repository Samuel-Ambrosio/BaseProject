import com.android.build.api.dsl.CommonExtension
import com.samuelav.buildlogic.intVersion
import com.samuelav.buildlogic.libs
import com.samuelav.buildlogic.library
import com.samuelav.buildlogic.version
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

// Shared by application and library modules. Must be applied after the Android plugin.
// The non-generic CommonExtension of AGP 9 exposes no DSL lambdas, hence the `.apply {}` blocks.

plugins {
    id("baseproject.detekt")
}

extensions.configure<CommonExtension> {
    compileSdk = libs.intVersion("compileSdk")

    defaultConfig.apply {
        minSdk = libs.intVersion("minSdk")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions.apply {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.toVersion(libs.version("jvmTarget"))
        targetCompatibility = JavaVersion.toVersion(libs.version("jvmTarget"))
    }

    packaging.apply {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

extensions.configure<KotlinAndroidProjectExtension> {
    jvmToolchain(libs.intVersion("jvmTarget"))
}

dependencies {
    "implementation"(libs.library("kotlin.coroutines.android"))
    "implementation"(libs.library("koin.core"))
    "implementation"(libs.library("androidx.core.ktx"))
    "coreLibraryDesugaring"(libs.library("android.desugar.jdk.libs"))

    "testImplementation"(libs.library("test.junit"))
    "androidTestImplementation"(libs.library("test.android.junit"))
    "androidTestImplementation"(libs.library("test.android.espresso"))
}
