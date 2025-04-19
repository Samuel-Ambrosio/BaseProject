package com.samuelav.presentation.common.app

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.samuelav.presentation.common.app.configuration.AppConfiguration
import com.samuelav.presentation.common.app.configuration.screen.AppBottomNavigationBarScreenConfig
import com.samuelav.presentation.common.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.presentation.common.app.configuration.screen.ScreenConfig
import com.samuelav.presentation.common.app.navigation.AppNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.koinInject

data class AppState(
    val appConfiguration: AppConfiguration,
    val appNavigation: AppNavigation,
    val appScope: CoroutineScope,
    val scaffoldState: ScaffoldState,
    val screenConfig: MutableStateFlow<ScreenConfig>,
)

@Composable
fun rememberAppState(
    appConfiguration: AppConfiguration = koinInject(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    navHostController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
): AppState =
    remember(appConfiguration) {
        AppState(
            appConfiguration = appConfiguration,
            appNavigation = AppNavigation(navHostController = navHostController),
            appScope = appScope,
            scaffoldState = scaffoldState,
            screenConfig =
                MutableStateFlow(
                    ScreenConfig(
                        appTopBarScreenConfig =
                            AppTopBarScreenConfig(
                                isVisible = appConfiguration.initialTopBarVisibility,
                            ),
                        appBottomNavigationBarScreenConfig =
                            AppBottomNavigationBarScreenConfig(
                                isVisible = appConfiguration.initialBottomBarVisibility,
                            ),
                    ),
                )
        )
    }