import com.samuelav.buildlogic.intVersion
import com.samuelav.buildlogic.libs

plugins {
    id("com.android.application")
    id("baseproject.android.base")
    id("baseproject.android.compose")
    id("baseproject.android.flavors")
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
