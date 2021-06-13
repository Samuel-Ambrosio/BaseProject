package com.ghrisav.data.api.session

import com.ghrisav.data.api.session.dto.CredentialsDTO
import com.ghrisav.data.model.session.TokenBO
import com.ghrisav.data.source.session.SessionApiDataSource

class SessionApiDataSourceImpl(private val sessionWs: SessionWs): SessionApiDataSource {
    override suspend fun login(username: String, password: String): TokenBO? {
        return sessionWs.login(CredentialsDTO(username, password))?.toBO()
    }
}