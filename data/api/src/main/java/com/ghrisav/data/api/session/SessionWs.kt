package com.ghrisav.data.api.session

import com.ghrisav.data.api.session.dto.CredentialsDTO
import com.ghrisav.data.api.session.dto.TokenDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface SessionWs {
    @POST("login")
    suspend fun login(@Body credentialsDTO: CredentialsDTO): TokenDTO?
}