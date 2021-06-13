package com.ghrisav.data.repository.base

import com.ghrisav.data.source.session.SessionApiDataSource
import com.ghrisav.data.source.session.SessionLocalDataSource

open class BaseRepository(
    private val local: SessionLocalDataSource,
    private val api: SessionApiDataSource
) {
    protected suspend fun getAccessToken(): String? {
        var token = local.getToken()
        if (token == null) {
            val username = local.getUsername()
            val password = local.getPassword()
            if (username != null && password != null) {
                token = api.login(username, password)
                token?.let { local.saveToken(it) }
            }
        }
        return token?.accessToken
    }
}