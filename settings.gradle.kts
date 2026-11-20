pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    // Lets Gradle download the JDK requested by `jvmToolchain(...)` when it is not installed locally.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Configured in gradle.properties (app.name)
rootProject.name = providers.gradleProperty("app.name").get().replace(Regex("\\s+"), "")
include(
    listOf(
        ":app",
        ":data:repositoryImpl",
        ":data:sources",
        ":data:sourcesImpl:remote",
        ":data:sourcesImpl:local",
        ":domain:model",
        ":domain:repository",
        ":domain:useCase",
        ":presentation:analytics",
        ":presentation:common",
        ":presentation:features:advert",
        ":presentation:features:home"
    )
)
