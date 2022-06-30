package com.samuelav.commonandroid.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager

fun Modifier.clearFocusOnClick(): Modifier = composed {
    with(LocalFocusManager.current) {
        clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
            clearFocus()
        }
    }
}