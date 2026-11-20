import com.samuelav.buildlogic.Namespaces

plugins {
    id("baseproject.android.compose.library")
}

android {
    namespace = Namespaces.Presentation.Analytics
}

dependencies {
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)
}
