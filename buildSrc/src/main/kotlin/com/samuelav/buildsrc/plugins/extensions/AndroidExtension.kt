package com.samuelav.buildsrc.plugins.extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

// For Android base plugins
internal fun Project.android(block: BaseExtension.() -> Unit) =
    block(extensions.getByType<BaseExtension>())
