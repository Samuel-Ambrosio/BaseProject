package com.samuelav.presentation.features.home.ui

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.samuelav.presentation.common.app.navigation.AppNavKey
import com.samuelav.presentation.common.extensions.appEntry

fun EntryProviderScope<NavKey>.homeNavEntries() {
    appEntry<AppNavKey.Home.Main> {
        MainScreen()
    }
}
