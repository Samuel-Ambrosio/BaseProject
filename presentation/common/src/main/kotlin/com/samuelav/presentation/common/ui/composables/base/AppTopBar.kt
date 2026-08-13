package com.samuelav.presentation.common.ui.composables.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.AppNavKey
import com.samuelav.presentation.common.extensions.safeRemoveLast
import com.samuelav.presentation.common.ui.theme.AppTheme.animations
import com.samuelav.presentation.common.ui.theme.AppTheme.colors
import com.samuelav.presentation.common.ui.theme.AppTheme.icons
import com.samuelav.presentation.common.ui.theme.AppTheme.spacing

@Composable
fun AppTopBar(
    title: String? = null,
    isBackButtonVisible: Boolean = false,
    onBackClick: (() -> Unit)?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colors.surface)
            .padding(horizontal = spacing.xxs),
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterStart),
            visible = isBackButtonVisible,
            enter = animations.slideInHorizontallyFromLeft,
            exit = animations.slideOutHorizontallyFromRight,
        ) {
            IconButton(onClick = { onBackClick?.invoke() }) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = icons.arrowBack.painter,
                    tint = colors.onSurface,
                    contentDescription = null,
                )
            }
        }

        title?.let {
            BodyLargeBold(
                modifier = Modifier.align(Alignment.Center),
                text = it,
            )
        }
    }
}
