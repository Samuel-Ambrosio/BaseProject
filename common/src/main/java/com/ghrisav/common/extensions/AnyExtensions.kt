package com.ghrisav.common.extensions

val Any.TAG: String
    get() {
        return javaClass.simpleName
    }