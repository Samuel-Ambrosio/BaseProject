package com.ghrisav.common.utils

class Event<out T>(private val content: T?) {

    private var contentHasBeenHandled = false

    fun hasContentBeenHandled() = contentHasBeenHandled

    fun getContent(): T? = content

    fun getContentIfNotHandled(): T? =
        if (contentHasBeenHandled) {
            null
        } else {
            contentHasBeenHandled = true
            content
        }
}