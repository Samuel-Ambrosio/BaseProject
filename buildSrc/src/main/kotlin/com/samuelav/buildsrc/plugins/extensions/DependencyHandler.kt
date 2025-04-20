package com.samuelav.buildsrc.plugins.extensions

import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

fun DependencyHandler.implementation(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency {
    return addDependencyTo(this, "implementation", dependencyNotation, dependencyConfiguration)
}

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? {
    return add("implementation", dependencyNotation)
}

fun DependencyHandler.api(dependencyNotation: Any): Dependency? {
    return add("api", dependencyNotation)
}

fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? {
    return add("debugImplementation", dependencyNotation)
}

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? {
    return add("testImplementation", dependencyNotation)
}

fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? {
    return add("testRuntimeOnly", dependencyNotation)
}

fun DependencyHandler.ksp(dependencyNotation: Any): Dependency? {
    return add("ksp", dependencyNotation)
}

fun DependencyHandler.kspAndroidTest(dependencyNotation: Any): Dependency? {
    return add("kspAndroidTest", dependencyNotation)
}

internal fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? {
    return add("androidTestImplementation", dependencyNotation)
}

internal fun DependencyHandler.coreLibraryDesugaring(dependencyNotation: Any): Dependency? {
    return add("coreLibraryDesugaring", dependencyNotation)
}
