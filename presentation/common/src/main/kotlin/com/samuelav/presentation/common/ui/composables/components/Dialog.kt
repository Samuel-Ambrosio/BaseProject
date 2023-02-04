package com.samuelav.presentation.common.ui.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.samuelav.presentation.common.ui.composables.base.BodyLargeBold
import com.samuelav.presentation.common.ui.composables.base.ButtonText
import com.samuelav.presentation.common.ui.theme.AppTheme.spacing

@Composable
fun Dialog(
    title: String,
    text: @Composable (() -> Unit)? = null,
    confirmButtonText: String? = null,
    cancelButtonText: String? = null,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Row(
                modifier = Modifier.padding(start = spacing.l, end = spacing.l, bottom = spacing.m),
                horizontalArrangement = Arrangement.spacedBy(spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                cancelButtonText?.let {
                    AppOutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = onDismiss,
                        borderColor = colors.onSurface.copy(alpha = 0.1f),
                        content = { ButtonText(text = it) })
                }

                confirmButtonText?.let {
                    AppButton(
                        modifier = Modifier.weight(1f),
                        onClick = onConfirm,
                        content = { ButtonText(text = it, color = colors.onPrimary) })
                }
            }
        },
        title = {
            BodyLargeBold(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center)
        },
        text = text,
        shape = shapes.large,
        backgroundColor = colors.surface)
}