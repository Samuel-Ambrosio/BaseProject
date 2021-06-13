package com.ghrisav.data.repository.session

import com.ghrisav.data.model.session.Session
import com.ghrisav.data.model.session.TokenBO
import com.ghrisav.data.repository.base.BaseRepository
import com.ghrisav.data.source.session.SessionApiDataSource
import com.ghrisav.data.source.session.SessionLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface SessionRepository {
    suspend fun login(username: String, password: String): Flow<TokenBO?>
    suspend fun getToken(): Flow<TokenBO?>
    suspend fun clearData(): Flow<Boolean>
}

class SessionRepositoryImpl(
    private val sessionLocalDataSource: SessionLocalDataSource,
    private val sessionApiDataSource: SessionApiDataSource
): BaseRepository(sessionLocalDataSource, sessionApiDataSource), SessionRepository {

    override suspend fun login(username: String, password: String): Flow<TokenBO?> {
        return flow {
            val token = sessionApiDataSource.login(username, password)

            token?.let {
                sessionLocalDataSource.saveUserAndToken(username, password, it)
                Session.setToken(it)
            }

            emit(token)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getToken(): Flow<TokenBO?> {
        return flow {
            val token = sessionLocalDataSource.getToken()

            token?.let { Session.setToken(it) }

            emit(token)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun clearData(): Flow<Boolean> {
        return flow {
            val isDataCleared = sessionLocalDataSource.clearData()

            if (isDataCleared) {
                Session.resetSession()
            }

            emit(isDataCleared)
        }.flowOn(Dispatchers.IO)
    }
}