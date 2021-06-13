package com.ghrisav.commonandroid.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.ghrisav.commonandroid.utils.SingleClickListener

/* Public ext functions */
fun View.doVisible() {
    visibility = View.VISIBLE
}

fun View.doGone() {
    visibility = View.GONE
}

fun View.doInvisible() {
    visibility = View.INVISIBLE
}

fun View.doVisibleOrGone(condition: () -> Boolean) {
    doVisibleOr(View.GONE, condition)
}

fun View.doVisibleOrInvisible(condition: () -> Boolean) {
    doVisibleOr(View.INVISIBLE, condition)
}

fun View?.setMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    this?.layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = this.toPx() }
        top?.run { topMargin = this.toPx() }
        right?.run { rightMargin = this.toPx() }
        bottom?.run { bottomMargin = this.toPx() }
    }
}

fun View.hideKeyboard() {
    val inputManager = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val inputManager = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.setOnSingleClickListener(action: () -> Unit) {
    setOnClickListener(SingleClickListener {
        action()
    })
}

/* Private ext functions */
private fun View.doVisibleOr(orVisibility: Int, condition: () -> Boolean) {
    visibility = if (condition()) View.VISIBLE else orVisibility
}

private inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}