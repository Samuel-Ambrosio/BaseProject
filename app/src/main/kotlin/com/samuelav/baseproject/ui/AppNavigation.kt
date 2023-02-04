package com.samuelav.baseproject.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.samuelav.presentation.common.app.AppState
import com.samuelav.presentation.common.app.rememberAppState
import com.samuelav.presentation.common.ui.theme.AppTheme.animations
import com.samuelav.presentation.common.ui.theme.AppTheme.colors
import com.samuelav.presentation.features.advert.AdvertBanner

@Composable
fun AppNavigation(appState: AppState = rememberAppState()) {
    val screenConfig by appState.screenConfig.collectAsStateWithLifecycle()

    Scaffold(
        scaffoldState = appState.scaffoldState,
        topBar = {
            AnimatedVisibility(
                visible = screenConfig.appTopBarScreenConfig.isVisible,
                enter = animations.slideInVerticallyFromTop,
                exit = animations.slideOutVerticallyFromBottom) {
                AppTopBar(
                    appState = appState,
                    appTopBarScreenConfig = screenConfig.appTopBarScreenConfig)
            }
         },
        bottomBar = {
            AnimatedVisibility(
                visible = screenConfig.appBottomNavigationBarScreenConfig.isVisible,
                enter = animations.slideInVerticallyFromBottom,
                exit = animations.slideOutVerticallyFromTop) {
                AppBottomNavigationBar(appState = appState)
            }
        },
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = shapes.medium,
                    backgroundColor = colors.primary)
            }
        },
        content = { innerPadding ->
            ConstraintLayout(modifier = Modifier.padding(paddingValues = innerPadding)) {
                val (content, advert) = createRefs()

                AppNavHost(
                    modifier = Modifier.constrainAs(content) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(advert.top)
                        height = Dimension.preferredWrapContent
                    },
                    appState = appState)

                AdvertBanner(
                    modifier = Modifier.constrainAs(advert) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    })
            }
        }
    )
}