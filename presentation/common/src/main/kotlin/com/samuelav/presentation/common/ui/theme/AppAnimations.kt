package com.samuelav.presentation.common.ui.theme

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavBackStackEntry

object AppAnimations {
    private const val DURATION = 600

    /** Navigation animations */
    val navFadeIn: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { fadeIn(animationSpec = tween(DURATION)) }

    val navFadeOut: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { fadeOut(animationSpec = tween(DURATION)) }

    val navSlideInLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(DURATION)) }

    val navSlideInRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(DURATION)) }

    val navSlideInUp: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(DURATION)) }

    val navSlideInDown: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(DURATION)) }

    val navSlideOutLeft: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(DURATION)) }

    val navSlideOutRight: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(DURATION)) }

    val navSlideOutUp: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(DURATION)) }

    val navSlideOutDown: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(DURATION)) }

    /** Animated view animations */
    val fadeIn: EnterTransition = fadeIn(animationSpec = tween(DURATION))
    val fadeOut: ExitTransition = fadeOut(animationSpec = tween(DURATION))

    val slideInHorizontallyFromLeft: EnterTransition = slideInHorizontally(animationSpec = tween(DURATION)) { -it }
    val slideInHorizontallyFromRight: EnterTransition = slideInHorizontally(animationSpec = tween(DURATION)) { it }
    val slideOutHorizontallyFromRight: ExitTransition = slideOutHorizontally(animationSpec = tween(DURATION)) { -it }
    val slideOutHorizontallyFromLeft: ExitTransition = slideOutHorizontally(animationSpec = tween(DURATION)) { it }

    val slideInVerticallyFromTop: EnterTransition = slideInVertically(animationSpec = tween(DURATION)) { -it }
    val slideInVerticallyFromBottom: EnterTransition = slideInVertically(animationSpec = tween(DURATION)) { it }
    val slideOutVerticallyFromTop: ExitTransition = slideOutVertically(animationSpec = tween(DURATION)) { it }
    val slideOutVerticallyFromBottom: ExitTransition = slideOutVertically(animationSpec = tween(DURATION)) { -it }
}