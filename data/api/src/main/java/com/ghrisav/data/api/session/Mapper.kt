package com.ghrisav.data.api.session

import com.ghrisav.data.api.session.dto.TokenDTO
import com.ghrisav.data.model.session.TokenBO

fun TokenDTO.toBO() = TokenBO(accessToken, tokenType, expiresIn)