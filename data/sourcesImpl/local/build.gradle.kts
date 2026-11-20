import com.samuelav.buildlogic.Namespaces

plugins {
    id("convention.android.library")
    alias(libs.plugins.ksp)
}

android {
    namespace = Namespaces.Data.SourcesImpl.Local
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.data.sources)

    implementation(libs.androidx.room.core.runtime)
    implementation(libs.androidx.room.core.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.datastore)
    implementation(libs.moshi.core)
}
