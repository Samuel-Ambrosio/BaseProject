plugins {
    id("convention.kotlin.library")
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.domain.repository)
}
