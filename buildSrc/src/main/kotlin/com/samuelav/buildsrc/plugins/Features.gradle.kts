import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.plugins.extensions.implementation
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

apply<AndroidComposeLibraryPlugin>()

dependencies {
    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Domain.UseCase))
    implementation(project(Modules.Presentation.Analytics))
    implementation(project(Modules.Presentation.Common))
}
