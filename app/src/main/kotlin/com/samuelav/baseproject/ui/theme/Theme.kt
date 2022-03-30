package com.samuelav.baseproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette =
    darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = Black,
        surface = Black,
        onPrimary = White,
        onSecondary = White,
        onBackground = White,
        onSurface = White
    )

private val LightColorPalette =
    lightColors(
        primary = Purple500,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = White,
        surface = White,
        onPrimary = White,
        onSecondary = Black,
        onBackground = Black,
        onSurface = Black
    )

@Composable
fun BaseTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}