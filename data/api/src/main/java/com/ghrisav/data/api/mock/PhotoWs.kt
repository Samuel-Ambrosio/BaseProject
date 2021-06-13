package com.ghrisav.data.api.mock

import com.ghrisav.data.api.mock.dto.AlbumDTO
import com.ghrisav.data.api.mock.dto.PhotoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoWs {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDTO>

    @GET("albums/{id}")
    suspend fun getAlbum(@Path("id") id: Long): AlbumDTO

    @GET("photos")
    suspend fun getPhotos(): List<PhotoDTO>

    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") id: Long): PhotoDTO
}