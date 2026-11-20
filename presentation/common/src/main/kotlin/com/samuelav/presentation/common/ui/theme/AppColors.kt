package com.samuelav.presentation.common.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

@ConsistentCopyVisibility
data class AppColors internal constructor(
    val info: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    // Material 3 has no "variant" roles, so they are kept here
    val primaryVariant: Color,
    val secondaryVariant: Color,
    val colorScheme: ColorScheme
) {
    val primary by lazy { colorScheme.primary }
    val secondary by lazy { colorScheme.secondary }

    val background by lazy { colorScheme.background }
    val surface by lazy { colorScheme.surface }

    val onPrimary by lazy { colorScheme.onPrimary }
    val onSecondary by lazy { colorScheme.onSecondary }
    val onBackground by lazy { colorScheme.onBackground }
    val onSurface by lazy { colorScheme.onSurface }
}
