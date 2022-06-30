package com.samuelav.commonandroid.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors

@Composable
fun HorizontalSeparator(modifier: Modifier = Modifier) {
    Spacer(modifier.fillMaxWidth().height(1.dp).background(colors.onSurface.copy(ContentAlpha.disabled)))
}

@Preview
@Composable
private fun HorizontalSeparatorPreview() {
    AppTheme {
        HorizontalSeparator()
    }
}