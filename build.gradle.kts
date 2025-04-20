import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.samuelav.buildsrc.setup.setup
import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.checkVersions)
    // TODO FIX DEPENDENCY UPDATE & GRAPH GENERATOR TASKS
    alias(libs.plugins.dependencyGraphGenerator)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    tasks {
        withType<Delete> { delete(layout.buildDirectory) }
    }
}

tasks {
    withType(DependencyUpdatesTask::setup)
}

// TODO FIX GRAPH GENERATOR TASK
configure<DependencyGraphGeneratorExtension> {
    generators.create("ProjectGraph") {
        include = { dependency -> dependency.moduleGroup.startsWith("BaseProject") }
        children = { _ -> true }
    }
}
