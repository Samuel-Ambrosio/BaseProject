import com.samuelav.buildsrc.app.AppConfig
import com.samuelav.buildsrc.plugins.extensions.implementation
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

val libs = the<LibrariesForLibs>()

apply(plugin = libs.plugins.kotlin.get().pluginId)

// Pin the bytecode level explicitly so pure Kotlin modules match the Android ones
// regardless of which JDK runs Gradle.
extensions.configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.toVersion(AppConfig.kotlinJvmTarget)
    targetCompatibility = JavaVersion.toVersion(AppConfig.kotlinJvmTarget)
}

extensions.configure<KotlinJvmProjectExtension> {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(AppConfig.kotlinJvmTarget))
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines)
    implementation(libs.koin.core)
}