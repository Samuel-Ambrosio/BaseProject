package com.ghrisav.data.local.session

import com.ghrisav.data.model.session.Session
import com.ghrisav.data.model.session.TokenBO
import com.ghrisav.data.local.session.PreferenceUtils
import com.ghrisav.data.source.session.SessionLocalDataSource

class SessionLocalDataSourceImpl(
    private val preferenceUtils: PreferenceUtils
) : SessionLocalDataSource {

    override suspend fun saveUserAndToken(username: String, password: String, token: TokenBO) {
        preferenceUtils.saveUserAndToken(username, password, token)
    }

    override suspend fun saveToken(token: TokenBO) {
        preferenceUtils.saveToken(token)
    }

    override suspend fun getToken(): TokenBO? {
        return preferenceUtils.getToken()
    }

    override suspend fun getUsername(): String? {
        return preferenceUtils.getUsername()
    }

    override suspend fun getPassword(): String? {
        return preferenceUtils.getPassword()
    }

    override suspend fun clearData(): Boolean {
        return preferenceUtils.clearData()
    }
}