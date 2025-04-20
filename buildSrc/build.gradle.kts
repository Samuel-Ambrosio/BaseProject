plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // [See](https://github.com/gradle/gradle/issues/15383#issuecomment-779893192)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.androidGradlePlugin)
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.composeCompilerGradlePlugin)
    implementation(libs.googleServicesGradlePlugin)
    implementation(libs.crashlyticsGradlePlugin)
    implementation(libs.checkVersionsGradlePlugin)
    implementation(libs.dependencyGraphGeneratorGradlePlugin)
}