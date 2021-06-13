package com.ghrisav.commonandroid.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.ghrisav.common.utils.Event
import com.ghrisav.commonandroid.extensions.toLiveData
import com.ghrisav.navigation.NavigationCommand
import kotlinx.coroutines.cancel

open class BaseViewModel: ViewModel() {

    protected val fullScreenLoading = MutableLiveData<Event<Boolean>>()
    protected val snackBarError = MutableLiveData<Event<String>>()
    private val navigation = MutableLiveData<Event<NavigationCommand>>()

    fun isLoadingFullScreen() = fullScreenLoading.toLiveData()
    fun isSnackBarError() = snackBarError.toLiveData()
    fun getNavigation() = navigation.toLiveData()

    fun navigate(directions: NavDirections) {
        navigation.value = Event(NavigationCommand.To(directions))
    }

    fun navigateBack() {
        navigation.value = Event(NavigationCommand.Back)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}