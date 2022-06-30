package com.samuelav.baseproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.samuelav.commonandroid.app.AppState
import com.samuelav.commonandroid.ui.composables.base.BodyLargeBold
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.theme.AppTheme.spacing

@Composable
fun AppTopBar(appState: AppState) {
    val screenConfig by appState.screenConfig
    val titleContent = screenConfig.appTopBarScreenConfig.titleContent
    val actionsContent = screenConfig.appTopBarScreenConfig.actions

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colors.primary)
            .padding(horizontal = spacing.xxs),
    ) {
        val (backIcon, title, actions) = createRefs()

        screenConfig.appTopBarScreenConfig.onBackClick?.let { onBackClick ->
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.constrainAs(backIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = icons.arrowBack.painter,
                    tint = colors.onSurface,
                    contentDescription = null)
            }
        }

        if (titleContent != null) {
            Row(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                content = titleContent)
        } else {
            BodyLargeBold(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                text = screenConfig.appTopBarScreenConfig.title)
        }

        if (actionsContent != null) {
            Row(
                modifier = Modifier.constrainAs(actions) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                content = actionsContent)
        }
    }
}