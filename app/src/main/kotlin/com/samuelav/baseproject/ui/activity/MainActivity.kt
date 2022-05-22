package com.samuelav.baseproject.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samuelav.baseproject.ui.AppNavigation
import com.samuelav.commonandroid.extensions.setAppThemeContent
import com.samuelav.commonandroid.ui.theme.AppTheme
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setAppThemeContent {
            val splashViewModel: SplashViewModel = get()
            val state by splashViewModel.state.collectAsState()

            AppTheme {
                when (state) {
                    is SplashState.Loading -> {} // TODO LOADING STATE SCREEN
                    is SplashState.AppNavigation -> AppNavigation()
                    is SplashState.OnBoarding -> {} // TODO ONBOARDING STATE SCREEN
                }
            }
        }
    }
}