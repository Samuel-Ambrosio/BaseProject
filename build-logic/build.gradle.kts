plugins {
    `kotlin-dsl`
}

dependencies {
    // compileOnly: the plugins themselves are put on the build classpath once, from the root
    // build.gradle.kts (`alias(...) apply false`), so every project shares the same classloader.
    compileOnly(libs.androidGradlePlugin)
    compileOnly(libs.kotlinGradlePlugin)
    compileOnly(libs.kotlinSerializationGradlePlugin)
    compileOnly(libs.composeCompilerGradlePlugin)
    compileOnly(libs.detektGradlePlugin)
}
