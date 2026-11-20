import com.samuelav.buildlogic.Namespaces

plugins {
    id("convention.android.feature")
    alias(libs.plugins.roborazzi)
}

android {
    namespace = Namespaces.Presentation.Features.Home
}

dependencies {
    testImplementation(projects.domain.repository)
    testImplementation(libs.test.roborazzi)
    testImplementation(libs.test.roborazzi.compose)
    testImplementation(libs.test.roborazzi.junit)
}

roborazzi {
    outputDir.set(file("src/test/screenshots"))
}
