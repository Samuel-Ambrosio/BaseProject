package com.samuelav.presentation.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*

@Composable
fun AppTheme(
    appAnimations: AppAnimations = AppAnimations,
    appColors: AppColors = if (isSystemInDarkTheme()) BaseDarkAppColors else BaseLightAppColors,
    appIconography: AppIconography = AppIconography,
    appSpacing: AppSpacing = AppSpacing(),
    appTypography: AppTypography = AppTypography(),
    content: @Composable () -> Unit
) {
    val providedValues =
        mutableListOf<ProvidedValue<*>>(
            LocalAppAnimations provides appAnimations,
            LocalAppColors provides appColors,
            LocalAppIconography provides appIconography,
            LocalAppSpacing provides appSpacing,
            LocalAppTypography provides appTypography)
    if (isSystemInDarkTheme()) providedValues.add(LocalElevationOverlay provides null)

    CompositionLocalProvider(*providedValues.toTypedArray()) {
        MaterialTheme(
            colors = appColors.materialColors,
            typography = appTypography.materialTypography,
            shapes = Shapes,
            content = content)
    }
}

object AppTheme {
    val animations: AppAnimations
        @Composable @ReadOnlyComposable get() = LocalAppAnimations.current
    val colors: AppColors
        @Composable @ReadOnlyComposable get() = LocalAppColors.current
    val icons: AppIconography
        @Composable @ReadOnlyComposable get() = LocalAppIconography.current
    val spacing: AppSpacing
        @Composable @ReadOnlyComposable get() = LocalAppSpacing.current
    val typography: AppTypography
        @Composable @ReadOnlyComposable get() = LocalAppTypography.current
}

private val LocalAppColors =
    staticCompositionLocalOf<AppColors> { error("No AppColors found!") }
private val LocalAppIconography =
    staticCompositionLocalOf<AppIconography> { error("No AppIconography found!") }
private val LocalAppSpacing =
    staticCompositionLocalOf<AppSpacing> { error("No AppSpacing found!") }
private val LocalAppTypography =
    staticCompositionLocalOf<AppTypography> { error("No AppTypography found!") }
val LocalAppAnimations =
    staticCompositionLocalOf<AppAnimations> { error("No AppAnimations found!") }