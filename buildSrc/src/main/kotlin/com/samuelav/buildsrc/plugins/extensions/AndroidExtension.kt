package com.samuelav.buildsrc.plugins.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

// For Android base plugins
internal fun Project.android(block: CommonExtension.() -> Unit) =
    block(extensions.getByType<CommonExtension>())
