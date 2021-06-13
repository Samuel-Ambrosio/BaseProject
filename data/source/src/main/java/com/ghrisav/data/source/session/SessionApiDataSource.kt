package com.ghrisav.data.source.session

import com.ghrisav.data.model.session.TokenBO

interface SessionApiDataSource {
    suspend fun login(username: String, password: String): TokenBO?
}