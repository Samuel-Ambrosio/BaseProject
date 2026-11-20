import com.samuelav.buildlogic.intVersion
import com.samuelav.buildlogic.libs

plugins {
    id("com.android.application")
    id("convention.android.base")
    id("convention.android.compose")
    id("convention.android.flavors")
}

android {
    defaultConfig {
        targetSdk = libs.intVersion("targetSdk")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}
