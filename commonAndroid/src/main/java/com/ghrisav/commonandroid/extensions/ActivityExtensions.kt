package com.ghrisav.commonandroid.extensions

import android.app.Activity

fun Activity?.getIfActive() =
    if (this.canUse()) {
        this
    } else {
        null
    }

fun Activity?.canUse() = this?.isFinishing == false