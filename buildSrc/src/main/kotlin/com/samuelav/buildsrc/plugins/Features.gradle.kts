import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.plugins.extensions.implementation

plugins {
    id("AndroidComposeLibrary")
}

dependencies {
    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Domain.UseCase))
    implementation(project(Modules.Presentation.Analytics))
    implementation(project(Modules.Presentation.Common))
}
