package com.samuelav.baseproject.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samuelav.baseproject.ui.AppScaffold
import com.samuelav.presentation.common.extensions.setAppThemeContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Remove '//' after google-services.json file and ad id are added
        //MobileAds.initialize(this)

        installSplashScreen()
        setAppThemeContent {
            AppScaffold(
                modifier =
                    Modifier.fillMaxSize()
                            .statusBarsPadding()
                            .navigationBarsPadding(),
            )
        }
    }
}