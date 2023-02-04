package com.samuelav.presentation.common.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalAnimationApi::class)
object AppAnimations {
    private const val DURATION = 600

    /** Navigation animations */
    val navSlideInLeft: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(DURATION)) }

    val navSlideInRight: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right, tween(DURATION)) }

    val navSlideInUp: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up, tween(DURATION)) }

    val navSlideInDown: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { slideIntoContainer(AnimatedContentScope.SlideDirection.Down, tween(DURATION)) }

    val navSlideOutLeft: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, tween(DURATION)) }

    val navSlideOutRight: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(DURATION)) }

    val navSlideOutUp: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Up, tween(DURATION)) }

    val navSlideOutDown: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, tween(DURATION)) }

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