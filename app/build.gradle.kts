import com.samuelav.buildlogic.Namespaces

plugins {
    id("convention.android.application")
    // TODO: Remove '//' after google-services.json file is added
    //alias(libs.plugins.googleServices)
    //alias(libs.plugins.crashlytics)
}

android {
    namespace = Namespaces.App

    buildFeatures {
        resValues = true
    }

    // App identity is configured in gradle.properties
    defaultConfig {
        applicationId = providers.gradleProperty("app.applicationId").get()
        resValue("string", "app_name", providers.gradleProperty("app.name").get().replace("'", "\\'"))
        versionCode = 1
        versionName = "0.0.1"
    }
}

dependencies {
    implementation(projects.data.repositoryImpl)
    implementation(projects.data.sources)
    implementation(projects.data.sourcesImpl.local)
    implementation(projects.data.sourcesImpl.remote)

    implementation(projects.domain.model)
    implementation(projects.domain.repository)
    implementation(projects.domain.useCase)

    implementation(projects.presentation.analytics)
    implementation(projects.presentation.common)
    implementation(projects.presentation.features.advert)
    implementation(projects.presentation.features.home)

    implementation(libs.androidx.splash.screen)
    implementation(libs.debug.timber)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics)

    // TODO: Remove '//' after google-services.json file and ad id are added
    //implementation(libs.google.services.ads)
}
