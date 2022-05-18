package com.samuelav.commonandroid.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.samuelav.commonandroid.R

data class AppIconography internal constructor(
    val chevronLeft: Icon = Icon(resId = R.drawable.ic_chevron_left),
    val chevronRight: Icon = Icon(resId = R.drawable.ic_chevron_right),
    val chevronUp: Icon = Icon(resId = R.drawable.ic_chevron_up),
    val chevronDown: Icon = Icon(resId = R.drawable.ic_chevron_down)
)

data class Icon(@DrawableRes val resId: Int) {
    val painter
        @Composable get() = painterResource(id = resId)
}