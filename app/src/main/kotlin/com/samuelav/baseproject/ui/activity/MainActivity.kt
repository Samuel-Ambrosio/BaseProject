package com.samuelav.baseproject.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samuelav.baseproject.ui.AppNavigation
import com.samuelav.presentation.common.extensions.setAppThemeContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Remove '//' after google-services.json file and ad id are added
        //MobileAds.initialize(this)

        installSplashScreen()
        setAppThemeContent { AppNavigation() }
    }
}