package com.samuelav.presentation.common.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

internal val celticBlueLight = Color(0xFF335FD1)
internal val blueberry = Color(0xFF4F7FFA)
internal val azureWhite = Color(0xFFD2DFFF)

internal val lotion = Color(0xFFFBFBFB)
internal val eerieBlack = Color(0xFF201D1D)
internal val raisinBlack = Color(0xFF242222)

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
                primary = celticBlueLight,
                primaryVariant = blueberry,
                secondary = azureWhite,
                secondaryVariant = azureWhite,
                background = Color.White,
                surface = lotion,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onSurface = eerieBlack,
                onBackground = eerieBlack,
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
                primary = blueberry,
                primaryVariant = celticBlueLight,
                secondary = azureWhite,
                secondaryVariant = azureWhite,
                background = eerieBlack,
                surface = raisinBlack,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onSurface = Color.White,
                onBackground = Color.White,
            )
    )