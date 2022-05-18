package com.samuelav.commonandroid.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val Purple200 = Color(0xFFBB86FC)
internal val Purple500 = Color(0xFF6200EE)
internal val Purple700 = Color(0xFF3700B3)
internal val Teal200 = Color(0xFF03DAC5)

internal val celticBlue = Color(0xFF2365C7)
internal val americanGreen = Color(0xFF49B336)
internal val chromeYellow = Color(0xFFFFAB00)
internal val darkPastelRed = Color(0xFFBA381B)
internal val frenchSkyBlue = Color(0xFF85A1FF)
internal val pastelGreen = Color(0xFF7ADB69)
internal val buffYellow = Color(0xFFF9D48A)
internal val melonRed = Color(0xFFFFB9A9)

internal val BaseLightAppColors =
    AppColors(
        info = celticBlue,
        success = americanGreen,
        warning = chromeYellow,
        error = darkPastelRed,
        materialColors =
            lightColors(
                primary = Purple500,
                primaryVariant = Purple700,
                secondary = Teal200,
                secondaryVariant = Teal200,
                background = Color.White,
                surface = Color.White,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onSurface = Color.Black,
                onBackground = Color.Black,
            )
    )

internal val BaseDarkAppColors =
    AppColors(
        info = frenchSkyBlue,
        success = pastelGreen,
        warning = buffYellow,
        error = melonRed,
        materialColors =
            darkColors(
                primary = Purple200,
                primaryVariant = Purple700,
                secondary = Teal200,
                secondaryVariant = Teal200,
                background = Color.Black,
                surface = Color.Black,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onSurface = Color.White,
                onBackground = Color.White,
            )
    )