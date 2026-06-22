package com.samuelav.presentation.common.ui.theme

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object AppAnimations {
    private const val DURATION = 600

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

    /** Navigation 3 animations */
    val none =
        ContentTransform(
            targetContentEnter = EnterTransition.None,
            initialContentExit = ExitTransition.None,
            sizeTransform = null,
        )

    val fade =
        ContentTransform(
            targetContentEnter = fadeIn(animationSpec = tween(DURATION)),
            initialContentExit =
                fadeOut(
                    animationSpec = tween(DURATION),
                    targetAlpha = 1f,
                ),
            sizeTransform = null,
        )

    val popFade =
        ContentTransform(
            targetContentEnter =
                fadeIn(
                    animationSpec = tween(DURATION),
                    initialAlpha = 1f,
                ),
            initialContentExit = fadeOut(animationSpec = tween(DURATION)),
            sizeTransform = null,
        )

    val slideHorizontally =
        ContentTransform(
            targetContentEnter =
                slideInHorizontally(
                    animationSpec = tween(DURATION),
                    initialOffsetX = { fullWidth -> fullWidth },
                ),
            initialContentExit =
                slideOutHorizontally(
                    animationSpec = tween(DURATION),
                    targetOffsetX = { fullWidth -> -fullWidth },
                ),
            sizeTransform = null,
        )

    val popSlideHorizontally =
        ContentTransform(
            targetContentEnter =
                slideInHorizontally(
                    animationSpec = tween(DURATION),
                    initialOffsetX = { fullWidth -> -fullWidth },
                ),
            initialContentExit =
                slideOutHorizontally(
                    animationSpec = tween(DURATION),
                    targetOffsetX = { fullWidth -> fullWidth },
                ),
            sizeTransform = null,
        )

    val slideVertically =
        ContentTransform(
            targetContentEnter =
                slideInVertically(
                    animationSpec = tween(DURATION),
                    initialOffsetY = { fullHeight -> fullHeight },
                ),
            initialContentExit =
                slideOutVertically(
                    animationSpec = tween(DURATION),
                    targetOffsetY = { fullHeight -> -fullHeight },
                ),
            sizeTransform = null,
        )

    val popSlideVertically =
        ContentTransform(
            targetContentEnter =
                slideInVertically(
                    animationSpec = tween(DURATION),
                    initialOffsetY = { fullHeight -> -fullHeight },
                ),
            initialContentExit =
                slideOutVertically(
                    animationSpec = tween(DURATION),
                    targetOffsetY = { fullHeight -> fullHeight },
                ),
            sizeTransform = null,
        )
}