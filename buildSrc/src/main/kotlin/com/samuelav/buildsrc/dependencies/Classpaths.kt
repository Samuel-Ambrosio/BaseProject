package com.samuelav.buildsrc.dependencies

object Classpaths {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.Gradle.plugin}"
    const val gradleKotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.core}"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.Gradle.versionsPlugin}"
    const val gradleDependencyGraphGenerator = "com.vanniktech:gradle-dependency-graph-generator-plugin:${Versions.Gradle.graphGenerator}"
}