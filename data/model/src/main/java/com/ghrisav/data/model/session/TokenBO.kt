package com.ghrisav.data.model.session

data class TokenBO(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long
)
