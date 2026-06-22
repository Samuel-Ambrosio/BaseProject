package com.samuelav.presentation.common.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppNavKey : NavKey {
    sealed interface Home : AppNavKey {
        @Serializable
        object Main : Home
    }

    sealed interface Search : AppNavKey {
        @Serializable
        object Main : Search
    }

    sealed interface More : AppNavKey {
        @Serializable
        object Main : More
    }
}