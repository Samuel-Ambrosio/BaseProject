package com.ghrisav.data.api.mock

import com.ghrisav.data.api.mock.dto.AlbumDTO
import com.ghrisav.data.api.mock.dto.PhotoDTO
import com.ghrisav.data.model.mock.AlbumBO
import com.ghrisav.data.model.mock.PhotoBO

fun AlbumDTO.toBO() = AlbumBO(userId, id, title)

fun PhotoDTO.toBO() = PhotoBO(albumId, id, title, url, thumbnailUrl)