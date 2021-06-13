package com.ghrisav.data.api.mock

import com.ghrisav.data.model.mock.AlbumBO
import com.ghrisav.data.model.mock.PhotoBO
import com.ghrisav.data.source.mock.PhotoApiDataSource

class PhotoApiDataSourceImpl(private val photoWs: PhotoWs) : PhotoApiDataSource {
    override suspend fun getAlbums(): List<AlbumBO> {
        return photoWs.getAlbums().map { it.toBO() }
    }

    override suspend fun getAlbum(id: Long): AlbumBO {
        return photoWs.getAlbum(id).toBO()
    }

    override suspend fun getPhotos(): List<PhotoBO> {
        return photoWs.getPhotos().map { it.toBO() }
    }

    override suspend fun getPhoto(id: Long): PhotoBO {
        return photoWs.getPhoto(id).toBO()
    }
}