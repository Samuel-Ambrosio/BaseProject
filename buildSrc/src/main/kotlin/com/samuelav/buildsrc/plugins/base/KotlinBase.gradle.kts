import com.samuelav.buildsrc.plugins.extensions.implementation
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("kotlin")
}

val libs = the<LibrariesForLibs>()

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines)
    implementation(libs.koin.core)
}