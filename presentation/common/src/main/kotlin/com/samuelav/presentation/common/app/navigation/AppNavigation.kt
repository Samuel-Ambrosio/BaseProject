package com.samuelav.presentation.common.app.navigation

import androidx.navigation.NavHostController
import com.samuelav.presentation.common.extensions.navigate

class AppNavigation(val navHostController: NavHostController) {

    fun navigateTo(
        navCommand: NavCommand,
        args: List<Any> = emptyList(),
        optionalArgs: Map<NavArg, Any> = emptyMap(),
        popUpOption: PopUpOption = PopUpOption.NONE
    ) {
        navHostController.navigate(
            route = navCommand.createRoute(args = args, optionalArgs = optionalArgs),
            popUpOption = popUpOption,
        )
    }

    fun navigateUp () {
        navHostController.navigateUp()
    }

    fun navigateToHomeMain() {
        navHostController.navigate(NavCommand.HomeSection.Main.route) {
            popUpTo(NavCommand.HomeSection.Main.route) { inclusive = true }
        }
    }
}

enum class PopUpOption {
    NONE,
    CURRENT,
    TO_HOME_MAIN,
}