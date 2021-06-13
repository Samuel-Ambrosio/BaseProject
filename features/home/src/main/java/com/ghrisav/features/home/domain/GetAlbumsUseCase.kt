package com.ghrisav.features.home.domain

import com.ghrisav.data.model.mock.AlbumBO
import com.ghrisav.data.repository.mock.PhotoRepository
import kotlinx.coroutines.flow.Flow

class GetAlbumsUseCase(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(): Flow<List<AlbumBO>> {
        return photoRepository.getAlbums()
    }
}