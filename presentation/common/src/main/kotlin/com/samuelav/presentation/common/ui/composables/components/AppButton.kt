package com.samuelav.presentation.common.ui.composables.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = colors.primary,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        content = content)
}