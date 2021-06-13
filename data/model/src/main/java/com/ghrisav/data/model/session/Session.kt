package com.ghrisav.data.model.session

object Session {
    private var token: TokenBO? = null

    fun setToken(newToken: TokenBO) { token = newToken }
    fun resetSession() { token = null }

    fun isUserLogged() = token != null
}