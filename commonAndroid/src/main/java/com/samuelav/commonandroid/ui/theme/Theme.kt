package com.samuelav.commonandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import org.koin.android.BuildConfig

@Composable
fun AppTheme(
    appColors: AppColors =
        if (isSystemInDarkTheme() && BuildConfig.DEBUG) BaseDarkAppColors else BaseLightAppColors,
    appTypography: AppTypography = AppTypography(),
    appSpacing: AppSpacing = AppSpacing(),
    appIconography: AppIconography = AppIconography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppColors provides appColors,
        LocalAppTypography provides appTypography,
        LocalAppSpacing provides appSpacing,
        LocalAppIconography provides appIconography
    ) {
        MaterialTheme(
            colors = appColors.materialColors,
            typography = appTypography.materialTypography,
            shapes = Shapes,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable @ReadOnlyComposable get() = LocalAppColors.current
    val typography: AppTypography
        @Composable @ReadOnlyComposable get() = LocalAppTypography.current
    val spacing: AppSpacing
        @Composable @ReadOnlyComposable get() = LocalAppSpacing.current
    val icons: AppIconography
        @Composable @ReadOnlyComposable get() = LocalAppIconography.current
}

private val LocalAppColors =
    staticCompositionLocalOf<AppColors> { error("No AppColors found!") }
private val LocalAppTypography =
    staticCompositionLocalOf<AppTypography> { error("No AppTypography found!") }
private val LocalAppSpacing =
    staticCompositionLocalOf<AppSpacing> { error("No AppSpacing found!") }
private val LocalAppIconography =
    staticCompositionLocalOf<AppIconography> { error("No AppIconography found!") }