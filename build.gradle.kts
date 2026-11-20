import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Puts every plugin on the build classpath once, so all projects share the same classloader.
// The convention plugins in build-logic only compile against them.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.checkVersions)
}

// Run with: ./gradlew dependencyUpdates --no-parallel
tasks.withType<DependencyUpdatesTask>().configureEach {
    notCompatibleWithConfigurationCache("gradle-versions-plugin does not support the configuration cache")
    // Only suggest pre-releases for dependencies that are already on a pre-release (e.g. detekt).
    rejectVersionIf { isNonStable(candidate.version) && !isNonStable(currentVersion) }
}

fun isNonStable(version: String): Boolean {
    val hasStableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val isPlainNumber = "^[0-9,.v-]+(-r)?$".toRegex().matches(version)
    return !(hasStableKeyword || isPlainNumber)
}
