package com.samuelav.commonandroid.ui.composables.base

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.typography

@Composable
fun Heading1(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.headingH1,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun Heading2(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.headingH2,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun BodyLargeBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.bodyLargeBold,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun BodyLargeRegular(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.bodyLargeRegular,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun BodyMediumBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.bodyMediumBold,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun BodyMediumRegular(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.bodyMediumRegular,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun BodySmallBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.bodySmallBold,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun BodySmallRegular(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.bodySmallRegular,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = colors.onSurface,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        modifier = modifier,
        style = typography.button,
        color = color,
        textDecoration = textDecoration,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis)
}