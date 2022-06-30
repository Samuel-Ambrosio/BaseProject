package com.samuelav.commonandroid.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val DURATION = 600

@OptIn(ExperimentalAnimationApi::class)
val AnimatedContentScope<NavBackStackEntry>.slideIn: EnterTransition
    get() = slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(DURATION))
@OptIn(ExperimentalAnimationApi::class)
val AnimatedContentScope<NavBackStackEntry>.slideOut: ExitTransition
    get() = slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, tween(DURATION))

@OptIn(ExperimentalAnimationApi::class)
val AnimatedContentScope<NavBackStackEntry>.slideInPop: EnterTransition
    get() = slideIntoContainer(AnimatedContentScope.SlideDirection.Right, tween(DURATION))
@OptIn(ExperimentalAnimationApi::class)
val AnimatedContentScope<NavBackStackEntry>.slideOutPop: ExitTransition
    get() = slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(DURATION))