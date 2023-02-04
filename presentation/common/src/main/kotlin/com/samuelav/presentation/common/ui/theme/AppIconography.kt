package com.samuelav.presentation.common.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.samuelav.presentation.common.R


/**
 *  [Icons reference](https://fonts.google.com/icons?icon.style=Rounded)
 */
object AppIconography {
    val home: Icon = Icon(resId = R.drawable.ic_home)
    val search: Icon = Icon(resId = R.drawable.ic_search)
    val settings: Icon = Icon(resId = R.drawable.ic_settings)
    val notifications: Icon = Icon(resId = R.drawable.ic_bell_outline)
    val more: Icon = Icon(resId = R.drawable.ic_dots_horizontal)
    val menu: Icon = Icon(resId = R.drawable.ic_menu)
    val close: Icon = Icon(resId = R.drawable.ic_close)
    val chevronLeft: Icon = Icon(resId = R.drawable.ic_chevron_left)
    val chevronRight: Icon = Icon(resId = R.drawable.ic_chevron_right)
    val chevronUp: Icon = Icon(resId = R.drawable.ic_chevron_up)
    val chevronDown: Icon = Icon(resId = R.drawable.ic_chevron_down)
    val arrowBack: Icon = Icon(resId = R.drawable.ic_arrow_back)
    val arrowForward: Icon = Icon(resId = R.drawable.ic_arrow_forward)
    val refresh: Icon = Icon(resId = R.drawable.ic_refresh)
    val info: Icon = Icon(resId = R.drawable.ic_information_outline)
    val infoVariant: Icon = Icon(resId = R.drawable.ic_information_variant)
    val modify: Icon = Icon(resId = R.drawable.ic_pencil_outline)
    val delete: Icon = Icon(resId = R.drawable.ic_delete_outline)
    val share: Icon = Icon(resId = R.drawable.ic_share_variant_outline)
    val email: Icon = Icon(resId = R.drawable.ic_email_outline)
    val bug: Icon = Icon(resId = R.drawable.ic_bug_outline)
    val data: Icon = Icon(resId = R.drawable.ic_data)
    val code: Icon = Icon(resId = R.drawable.ic_code)
    val alert: Icon = Icon(resId = R.drawable.ic_alert_outline)
}

data class Icon(@DrawableRes val resId: Int) {
    val painter
        @Composable get() = painterResource(id = resId)
}