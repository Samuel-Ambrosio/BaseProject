package com.samuelav.presentation.common.ui.composables.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.samuelav.presentation.common.ui.theme.AppTheme.colors

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
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        content = content
    )
}
