package com.ghrisav.features.home.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ghrisav.commonandroid.extensions.execute
import com.ghrisav.commonandroid.extensions.toLiveData
import com.ghrisav.commonandroid.ui.viewmodel.BaseViewModel
import com.ghrisav.data.model.mock.AlbumBO
import com.ghrisav.features.home.domain.GetAlbumsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val getAlbumsUseCase: GetAlbumsUseCase) : BaseViewModel() {
    private val albums = MutableLiveData<List<AlbumBO>>()
    fun getAlbums() = albums.toLiveData()

    fun fetchAlbums() {
        viewModelScope.launch {
            getAlbumsUseCase().execute(albums, fullScreenLoading, snackBarError)
        }
    }
}