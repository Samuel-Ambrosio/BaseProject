import com.samuelav.buildsrc.app.Modules
import com.samuelav.buildsrc.app.Namespaces

plugins { AndroidLibrary }

android {
    namespace = Namespaces.Data.SourcesImpl.Remote
}

dependencies {
    implementation(project(Modules.Domain.Model))
    implementation(project(Modules.Data.Sources))

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.okHttp3.interceptor)
}