package com.samuelav.commonandroid.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.samuelav.commonandroid.app.configuration.AppConfiguration
import com.samuelav.commonandroid.app.configuration.screen.AppBottomNavigationBarScreenConfig
import com.samuelav.commonandroid.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.commonandroid.app.configuration.screen.ScreenConfig
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.get

data class AppState(
    val appConfiguration: AppConfiguration,
    val navController: NavHostController,
    val appScope: CoroutineScope,
    val scaffoldState: ScaffoldState,
    val screenConfig: MutableState<ScreenConfig>,
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    appConfiguration: AppConfiguration = get(),
    navController: NavHostController = rememberAnimatedNavController(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
): AppState =
    remember(appConfiguration, navController, scaffoldState) {
        AppState(
            appConfiguration = appConfiguration,
            navController = navController,
            appScope = appScope,
            scaffoldState = scaffoldState,
            screenConfig =
                mutableStateOf(
                    ScreenConfig(
                        appTopBarScreenConfig =
                            AppTopBarScreenConfig(
                                title = "",
                                isVisible = appConfiguration.initialTopBarVisibility),
                        appBottomNavigationBarScreenConfig =
                            AppBottomNavigationBarScreenConfig(
                                isVisible = appConfiguration.initialBottomBarVisibility))),
        )
    }


