import com.samuelav.buildlogic.intVersion
import com.samuelav.buildlogic.libs
import com.samuelav.buildlogic.library

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("convention.detekt")
}

kotlin {
    // Compiles with a JDK of this version (downloaded by foojay if missing) and targets the same bytecode,
    // so the pure Kotlin modules match the Android ones regardless of which JDK runs Gradle.
    jvmToolchain(libs.intVersion("jvmTarget"))
}

dependencies {
    implementation(libs.library("kotlin.coroutines.core"))
    implementation(libs.library("koin.core"))
}
