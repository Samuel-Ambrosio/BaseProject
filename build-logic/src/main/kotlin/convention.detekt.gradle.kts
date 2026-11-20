import com.samuelav.buildlogic.libs
import com.samuelav.buildlogic.library
import com.samuelav.buildlogic.version
import dev.detekt.gradle.Detekt

plugins {
    id("dev.detekt")
}

detekt {
    buildUponDefaultConfig.set(true)
    config.setFrom(isolated.rootProject.projectDirectory.file("config/detekt/detekt.yml"))
    parallel.set(true)
}

dependencies {
    detektPlugins(libs.library("detekt.ktlint"))
}

tasks.withType<Detekt>().configureEach {
    jvmTarget.set(libs.version("jvmTarget"))
    // Run with -PdetektAutoCorrect=true to let the ktlint ruleset fix issues in place.
    autoCorrect.set(providers.gradleProperty("detektAutoCorrect").map { it.toBoolean() }.orElse(false))
    reports {
        html.required.set(true)
        checkstyle.required.set(true)
        sarif.required.set(true)
        markdown.required.set(false)
    }
}
