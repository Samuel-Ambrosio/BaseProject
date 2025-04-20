package com.samuelav.buildsrc.setup

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

fun DependencyUpdatesTask.setup() = rejectVersionIf { candidate.version.isUnstable }

private val String.isUnstable: Boolean
    get() =
        unstableDependencies.any { filterValue: String ->
            this.contains(filterValue, ignoreCase = true)
        }

private val unstableDependencies: List<String> = listOf("rc", "alpha", "beta")