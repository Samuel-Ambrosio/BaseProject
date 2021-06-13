package com.ghrisav.data.repository.mock

import com.ghrisav.data.model.mock.AlbumBO
import com.ghrisav.data.model.mock.PhotoBO
import com.ghrisav.data.repository.base.BaseRepository
import com.ghrisav.data.source.mock.PhotoApiDataSource
import com.ghrisav.data.source.session.SessionApiDataSource
import com.ghrisav.data.source.session.SessionLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface PhotoRepository {
    suspend fun getAlbums(): Flow<List<AlbumBO>>
    suspend fun getAlbum(id: Long): Flow<AlbumBO>
    suspend fun getPhotos(): Flow<List<PhotoBO>>
    suspend fun getPhoto(id: Long): Flow<PhotoBO>
}

class PhotoRepositoryImpl(
    sessionLocalDataSource: SessionLocalDataSource,
    sessionApiDataSource: SessionApiDataSource,
    private val photoApiDataSource: PhotoApiDataSource
): BaseRepository(sessionLocalDataSource, sessionApiDataSource), PhotoRepository {

    override suspend fun getAlbums(): Flow<List<AlbumBO>> {
        return flow { emit(photoApiDataSource.getAlbums()) }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAlbum(id: Long): Flow<AlbumBO> {
        return flow { emit(photoApiDataSource.getAlbum(id)) }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPhotos(): Flow<List<PhotoBO>> {
        return flow { emit(photoApiDataSource.getPhotos()) }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPhoto(id: Long): Flow<PhotoBO> {
        return flow { emit(photoApiDataSource.getPhoto(id)) }.flowOn(Dispatchers.IO)
    }
}