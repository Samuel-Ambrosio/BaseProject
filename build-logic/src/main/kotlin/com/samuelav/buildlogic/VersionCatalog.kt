package com.samuelav.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

/** The `libs` version catalog of the build that applies the convention plugin. */
val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.library(alias: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(alias).orElseThrow { IllegalArgumentException("Library '$alias' not found in libs.versions.toml") }

fun VersionCatalog.version(alias: String): String =
    findVersion(alias)
        .orElseThrow { IllegalArgumentException("Version '$alias' not found in libs.versions.toml") }
        .requiredVersion

fun VersionCatalog.intVersion(alias: String): Int = version(alias).toInt()
