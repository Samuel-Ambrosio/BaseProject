package com.ghrisav.app.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ghrisav.app.domain.GetTokenUseCase
import com.ghrisav.commonandroid.extensions.execute
import com.ghrisav.commonandroid.extensions.toLiveData
import com.ghrisav.commonandroid.ui.viewmodel.BaseViewModel
import com.ghrisav.data.model.session.TokenBO
import kotlinx.coroutines.launch

class SplashViewModel(private val getTokenUseCase: GetTokenUseCase): BaseViewModel() {

    private val token = MutableLiveData<TokenBO?>()
    fun getToken() = token.toLiveData()

    fun fetchToken() {
        viewModelScope.launch {
            getTokenUseCase().execute(token)
        }
    }
}