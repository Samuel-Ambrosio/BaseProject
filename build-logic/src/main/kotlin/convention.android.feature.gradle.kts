plugins {
    id("convention.android.compose.library")
}

dependencies {
    implementation(project(":domain:model"))
    implementation(project(":domain:useCase"))
    implementation(project(":presentation:analytics"))
    implementation(project(":presentation:common"))
}
