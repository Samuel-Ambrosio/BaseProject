import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.samuelav.buildsrc.app.AppConfig
import com.samuelav.buildsrc.setup.setup
import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension
import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import org.gradle.api.artifacts.VersionCatalogsExtension

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
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.checkVersions)
    alias(libs.plugins.detekt) apply false
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

val versionCatalog = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")

subprojects {
    apply(plugin = "dev.detekt")

    extensions.configure<DetektExtension> {
        buildUponDefaultConfig.set(true)
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        parallel.set(true)
    }

    dependencies {
        "detektPlugins"(versionCatalog.findLibrary("detekt.ktlint").get())
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget.set(AppConfig.kotlinJvmTarget)
        // Run with -PdetektAutoCorrect=true to let the ktlint ruleset fix issues in place.
        autoCorrect.set(providers.gradleProperty("detektAutoCorrect").map { it.toBoolean() }.orElse(false))
        reports {
            html.required.set(true)
            checkstyle.required.set(true)
            sarif.required.set(true)
            markdown.required.set(false)
        }
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
