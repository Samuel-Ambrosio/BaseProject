package com.samuelav.baseproject.ui.activity

import androidx.lifecycle.viewModelScope
import com.samuelav.commonandroid.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashViewModel: BaseViewModel<SplashState, Unit>(SplashState.Loading) {
    init {
        viewModelScope.launch {
            delay(600)
            emitState(SplashState.AppNavigation)
        }
    }
}

internal sealed class SplashState {
    object Loading: SplashState()
    object AppNavigation: SplashState()
    object OnBoarding: SplashState()
}