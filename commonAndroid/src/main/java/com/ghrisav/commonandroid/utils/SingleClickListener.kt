package com.ghrisav.commonandroid.utils

import android.os.SystemClock
import android.view.View

class SingleClickListener<T>(
    private val disableClickInterval: Int = 1000,
    private val onClickListener: () -> T
): View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < disableClickInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onClickListener()
    }
}