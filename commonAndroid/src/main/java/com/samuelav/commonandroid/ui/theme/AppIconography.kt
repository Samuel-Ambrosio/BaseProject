package com.samuelav.commonandroid.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.samuelav.commonandroid.R

/**
 *  [Icons reference](https://fonts.google.com/icons?icon.style=Rounded)
 */
object AppIconography {
    val home: Icon = Icon(resId = R.drawable.ic_home)
    val search: Icon = Icon(resId = R.drawable.ic_search)
    val settings: Icon = Icon(resId = R.drawable.ic_settings)
    val menu: Icon = Icon(resId = R.drawable.ic_menu)
    val close: Icon = Icon(resId = R.drawable.ic_close)
    val chevronLeft: Icon = Icon(resId = R.drawable.ic_chevron_left)
    val chevronRight: Icon = Icon(resId = R.drawable.ic_chevron_right)
    val chevronUp: Icon = Icon(resId = R.drawable.ic_chevron_up)
    val chevronDown: Icon = Icon(resId = R.drawable.ic_chevron_down)
}

data class Icon(@DrawableRes val resId: Int) {
    val painter
        @Composable get() = painterResource(id = resId)
}