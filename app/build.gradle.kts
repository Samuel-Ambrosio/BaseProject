import com.samuelav.buildsrc.app.AppConfig
import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.app.Namespaces

plugins {
    alias(libs.plugins.androidApplication)
    KotlinAndroidComposeBase
    // TODO: Remove '//' after google-services.json file is added
    //alias(libs.plugins.googleServices)
    //alias(libs.plugins.crashlytics)
}

android {
    namespace = Namespaces.App

    defaultConfig {
        applicationId = AppConfig.applicationId
    }
}

dependencies {
    implementation(project(Modules.Data.RepositoryImpl))
    implementation(project(Modules.Data.Sources))
    implementation(project(Modules.Data.SourcesImpl.Local))
    implementation(project(Modules.Data.SourcesImpl.Remote))

    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Domain.Repository))
    implementation(project(Modules.Domain.UseCase))

    implementation(project(Modules.Presentation.Analytics))
    implementation(project(Modules.Presentation.Common))
    implementation(project(Modules.Presentation.Features.Advert))
    implementation(project(Modules.Presentation.Features.Home))

    implementation(libs.androidx.splash.screen)
    implementation(libs.debug.timber)
}