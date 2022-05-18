package com.samuelav.baseproject.ui.activity

import androidx.lifecycle.viewModelScope
import com.samuelav.commonandroid.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashViewModel: BaseViewModel<SplashState, SplashCommand>(SplashState.Loading) {
    init {
        viewModelScope.launch {
            delay(600)
            emitState(SplashState.Success)
        }
    }
}

internal sealed class SplashState {
    object Success: SplashState()
    object Error: SplashState()
    object Loading: SplashState()
}

internal sealed class SplashCommand {
    object NavigateToHome: SplashCommand()
    object NavigateToOnBoarding: SplashCommand()
}