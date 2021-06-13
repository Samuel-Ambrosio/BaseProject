package com.ghrisav.data.api.mock.dto

import com.google.gson.annotations.SerializedName

data class AlbumDTO(
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String
)