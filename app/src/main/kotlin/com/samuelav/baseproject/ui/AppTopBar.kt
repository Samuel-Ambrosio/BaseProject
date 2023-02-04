package com.samuelav.baseproject.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.configuration.screen.AppTopBarScreenConfig
import com.samuelav.presentation.common.ui.composables.base.BodyLargeBold
import com.samuelav.presentation.common.ui.theme.AppTheme.animations
import com.samuelav.presentation.common.ui.theme.AppTheme.colors
import com.samuelav.presentation.common.ui.theme.AppTheme.icons
import com.samuelav.presentation.common.ui.theme.AppTheme.spacing

@Composable
fun AppTopBar(appState: AppState, appTopBarScreenConfig: AppTopBarScreenConfig) {
    val onBackClick: () -> Unit =
        appTopBarScreenConfig.onBackClick ?: { appState.navController.navigateUp() }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colors.surface)
            .padding(horizontal = spacing.xxs),
    ) {
        val (backButton, title) = createRefs()

        AnimatedVisibility(
            modifier = Modifier.constrainAs(backButton) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            visible = appTopBarScreenConfig.isBackButtonVisible,
            enter = animations.slideInHorizontallyFromLeft,
            exit = animations.slideOutHorizontallyFromRight
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = icons.arrowBack.painter,
                    tint = colors.onSurface,
                    contentDescription = null)
            }
        }

        BodyLargeBold(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            text = appTopBarScreenConfig.title)
    }
}