package com.ghrisav.data.api.session.dto

data class TokenDTO(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long
)