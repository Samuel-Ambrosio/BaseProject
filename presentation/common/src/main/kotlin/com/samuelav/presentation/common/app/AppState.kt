package com.samuelav.presentation.common.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.samuelav.presentation.common.app.configuration.AppConfiguration
import com.samuelav.presentation.common.app.configuration.screen.AppBottomNavigationBarScreenConfig
import com.samuelav.presentation.common.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.presentation.common.app.configuration.screen.ScreenConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.get

data class AppState(
    val appConfiguration: AppConfiguration,
    val appScope: CoroutineScope,
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val screenConfig: MutableStateFlow<ScreenConfig>,
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    appConfiguration: AppConfiguration = get(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberAnimatedNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
): AppState =
    remember(appConfiguration, navController, scaffoldState) {
        AppState(
            appConfiguration = appConfiguration,
            appScope = appScope,
            navController = navController,
            scaffoldState = scaffoldState,
            screenConfig =
                MutableStateFlow(
                    ScreenConfig(
                        appTopBarScreenConfig =
                            AppTopBarScreenConfig(
                                isVisible = appConfiguration.initialTopBarVisibility),
                        appBottomNavigationBarScreenConfig =
                            AppBottomNavigationBarScreenConfig(
                                isVisible = appConfiguration.initialBottomBarVisibility)))
        )
    }