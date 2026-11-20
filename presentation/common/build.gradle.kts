import com.samuelav.buildlogic.Namespaces

plugins {
    id("convention.android.compose.library")
}

android {
    namespace = Namespaces.Presentation.Common
}

dependencies {
    implementation(projects.domain.model)

    // XML BaseTheme (Theme.MaterialComponents) used by the splash screen
    implementation(libs.google.material)
}
