package com.samuelav.presentation.common.app

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.samuelav.presentation.common.app.configuration.AppConfiguration
import com.samuelav.presentation.common.app.navigation.AppNavKey
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.koinInject

@Stable
data class AppState(
    val appConfiguration: AppConfiguration,
    val appScope: CoroutineScope,
    val navBackStack: NavBackStack<NavKey>,
    val scaffoldState: ScaffoldState,
)

@Composable
fun rememberAppState(
    appConfiguration: AppConfiguration = koinInject(),
    appScope: CoroutineScope = rememberCoroutineScope(),
    navBackStack: NavBackStack<NavKey> = rememberNavBackStack(AppNavKey.Home.Main),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
): AppState =
    remember(appConfiguration) {
        AppState(
            appConfiguration = appConfiguration,
            appScope = appScope,
            navBackStack = navBackStack,
            scaffoldState = scaffoldState,
        )
    }
