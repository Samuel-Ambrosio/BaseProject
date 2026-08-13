import com.samuelav.buildsrc.app.AppConfig
import com.samuelav.buildsrc.plugins.extensions.android
import com.samuelav.buildsrc.plugins.extensions.androidTestImplementation
import com.samuelav.buildsrc.plugins.extensions.coreLibraryDesugaring
import com.samuelav.buildsrc.plugins.extensions.implementation
import com.samuelav.buildsrc.plugins.extensions.testImplementation
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig.apply {
        minSdk = AppConfig.minSdkVersion

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "environment"
    productFlavors {
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

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures.apply {
        buildConfig = true
    }

    compileOptions.apply {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    packaging.apply {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines)
    implementation(libs.koin.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.google.material)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics)

    // TODO: Remove '//' after google-services.json file and ad id are added
    //implementation(libs.google.services.ads)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    coreLibraryDesugaring(libs.android.desugar.jdk.libs)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.android.junit)
    androidTestImplementation(libs.test.android.espresso)
}