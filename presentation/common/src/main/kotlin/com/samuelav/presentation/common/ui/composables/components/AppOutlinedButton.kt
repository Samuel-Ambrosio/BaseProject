package com.samuelav.presentation.common.ui.composables.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samuelav.presentation.common.ui.theme.AppTheme.colors

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    borderColor: Color = colors.primary,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(width = 0.5.dp, color = borderColor),
        content = content
    )
}
