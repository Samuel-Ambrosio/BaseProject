import com.samuelav.buildlogic.Namespaces

plugins {
    id("convention.android.library")
    id("convention.android.flavors")
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.Data.SourcesImpl.Remote
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.data.sources)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.moshi.core)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.okHttp3.interceptor)
}
