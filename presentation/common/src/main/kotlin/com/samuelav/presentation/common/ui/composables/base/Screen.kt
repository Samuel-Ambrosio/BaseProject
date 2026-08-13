package com.samuelav.presentation.common.ui.composables.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.navigation.AppNavKey
import com.samuelav.presentation.common.extensions.safeRemoveLast
import com.samuelav.presentation.common.ui.theme.AppTheme.animations

@Composable
fun Screen(
    titleTopBar: String? = null,
    isTopBarVisible: Boolean = true,
    isBackButtonVisible: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isTopBarVisible,
            enter = animations.topBarVisibilityEnter,
            exit = animations.topBarVisibilityExit,
        ) {
            AppTopBar(
                title = titleTopBar,
                isBackButtonVisible = isBackButtonVisible,
                onBackClick = onBackClick,
            )
        }

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            content.invoke()
        }
    }
}
