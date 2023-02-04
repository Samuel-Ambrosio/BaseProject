package com.samuelav.presentation.common.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.samuelav.presentation.common.R
import androidx.compose.material.Typography as MaterialTypography

data class AppTypography internal constructor(
    val headingH1: TextStyle,
    val headingH2: TextStyle,
    val bodyLargeBold: TextStyle,
    val bodyLargeRegular: TextStyle,
    val bodyMediumBold: TextStyle,
    val bodyMediumRegular: TextStyle,
    val bodySmallBold: TextStyle,
    val bodySmallRegular: TextStyle,
    val button: TextStyle,
    val materialTypography: MaterialTypography
) {
    constructor(
        fontFamily: FontFamily = FontFamily(
            Font(R.font.roboto_bold, FontWeight.Bold),
            Font(R.font.roboto_black, FontWeight.Black),
            Font(R.font.roboto_regular, FontWeight.Normal),
        ),
        headingH1: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        ),
        headingH2: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        ),
        bodyLargeBold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        ),
        bodyLargeRegular: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        ),
        bodyMediumBold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        bodyMediumRegular: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        bodySmallBold: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        ),
        bodySmallRegular: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        ),
        button: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
    ): this(
        headingH1 = headingH1.copy(fontFamily = fontFamily),
        headingH2 = headingH2.copy(fontFamily = fontFamily),
        bodyLargeBold = bodyLargeBold.copy(fontFamily = fontFamily),
        bodyLargeRegular = bodyLargeRegular.copy(fontFamily = fontFamily),
        bodyMediumBold = bodyMediumBold.copy(fontFamily = fontFamily),
        bodyMediumRegular = bodyMediumRegular.copy(fontFamily = fontFamily),
        bodySmallBold = bodySmallBold.copy(fontFamily = fontFamily),
        bodySmallRegular= bodySmallRegular.copy(fontFamily = fontFamily),
        button = button.copy(fontFamily = fontFamily),
        materialTypography =
            MaterialTypography(
                defaultFontFamily = fontFamily,
                h1 = headingH1,
                h2 = headingH2,
                subtitle1 = bodyLargeBold,
                subtitle2 = bodyMediumBold,
                body1 = bodyLargeRegular,
                body2 = bodyMediumRegular,
                button = button,
                caption = bodySmallRegular,
            ),
    )
}