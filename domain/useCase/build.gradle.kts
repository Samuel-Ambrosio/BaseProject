import com.samuelav.buildsrc.app.Modules

plugins { KotlinLibrary }

dependencies {
    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Domain.Repository))
}