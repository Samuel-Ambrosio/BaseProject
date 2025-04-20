import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.app.Namespaces

plugins { AndroidComposeLibrary }

android {
    namespace = Namespaces.Presentation.Common
}

dependencies {
    implementation(project(Modules.Domain.Model))
}