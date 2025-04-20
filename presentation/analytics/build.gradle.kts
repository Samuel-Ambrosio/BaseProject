import com.samuelav.buildsrc.app.Namespaces

plugins { AndroidComposeLibrary }

android {
    namespace = Namespaces.Presentation.Analytics
}

dependencies {
    implementation(libs.google.firebase.analytics)
}