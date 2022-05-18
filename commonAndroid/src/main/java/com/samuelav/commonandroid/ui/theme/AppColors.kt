package com.samuelav.commonandroid.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material.Colors as MaterialColors

data class AppColors internal constructor(
    val info: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val materialColors: MaterialColors
) {
    val primary by lazy { materialColors.primary }
    val primaryVariant by lazy { materialColors.primaryVariant }
    val secondary by lazy { materialColors.secondary }
    val secondaryVariant by lazy { materialColors.secondaryVariant }

    val background by lazy { materialColors.background }
    val surface by lazy { materialColors.surface }

    val onPrimary by lazy { materialColors.onPrimary }
    val onSecondary by lazy { materialColors.onSecondary }
    val onBackground by lazy { materialColors.onBackground }
    val onSurface by lazy { materialColors.onSurface }
}