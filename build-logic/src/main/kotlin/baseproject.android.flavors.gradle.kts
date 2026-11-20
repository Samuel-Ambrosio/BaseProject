import com.android.build.api.dsl.CommonExtension

// Environment flavors. Only for the modules that need per-environment values (the app and the
// remote data source); the rest of the libraries stay flavorless and Gradle matches them anyway.

extensions.configure<CommonExtension> {
    buildFeatures.buildConfig = true

    flavorDimensions += "environment"
    productFlavors.apply {
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_PATH", "\"https://jsonplaceholder.typicode.com/\"")
        }
        create("pre") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_PATH", "\"https://jsonplaceholder.typicode.com/\"")
        }
        create("pro") {
            dimension = "environment"
            buildConfigField("String", "API_BASE_PATH", "\"https://jsonplaceholder.typicode.com/\"")
        }
    }
}
