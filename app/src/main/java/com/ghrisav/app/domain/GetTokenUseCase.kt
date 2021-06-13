package com.ghrisav.app.domain

import com.ghrisav.data.model.session.TokenBO
import com.ghrisav.data.repository.session.SessionRepository
import kotlinx.coroutines.flow.Flow

class GetTokenUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(): Flow<TokenBO?> {
        return sessionRepository.getToken()
    }
}