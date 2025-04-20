import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.app.Namespaces
import com.samuelav.buildsrc.plugins.extensions.ksp

plugins {
    AndroidLibrary
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.Data.SourcesImpl.Local
}

dependencies {
    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Data.Sources))

    implementation(libs.androidx.room.core.runtime)
    implementation(libs.androidx.room.core.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore)
    implementation(libs.google.gson)
}