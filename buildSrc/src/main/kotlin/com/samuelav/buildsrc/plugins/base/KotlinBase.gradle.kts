import com.samuelav.buildsrc.plugins.extensions.implementation
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

apply(plugin = libs.plugins.kotlin.get().pluginId)

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines)
    implementation(libs.koin.core)
}