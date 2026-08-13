import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.app.Namespaces
import com.samuelav.buildsrc.plugins.extensions.ksp

plugins {
    AndroidLibrary
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.Data.SourcesImpl.Remote
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Data.Sources))

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.moshi.core)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.okHttp3.interceptor)
}