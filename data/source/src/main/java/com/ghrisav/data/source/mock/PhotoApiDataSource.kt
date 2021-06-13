package com.ghrisav.data.source.mock

import com.ghrisav.data.model.mock.AlbumBO
import com.ghrisav.data.model.mock.PhotoBO

interface PhotoApiDataSource {
    suspend fun getAlbums(): List<AlbumBO>
    suspend fun getAlbum(id: Long): AlbumBO
    suspend fun getPhotos(): List<PhotoBO>
    suspend fun getPhoto(id: Long): PhotoBO
}