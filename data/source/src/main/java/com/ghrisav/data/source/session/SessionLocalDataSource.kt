package com.ghrisav.data.source.session

import com.ghrisav.data.model.session.TokenBO

interface SessionLocalDataSource {
    suspend fun saveUserAndToken(username: String, password: String, token: TokenBO)
    suspend fun saveToken(token: TokenBO)
    suspend fun getToken(): TokenBO?
    suspend fun getUsername(): String?
    suspend fun getPassword(): String?
    suspend fun clearData(): Boolean
}