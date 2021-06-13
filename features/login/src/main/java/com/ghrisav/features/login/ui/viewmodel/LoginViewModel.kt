package com.ghrisav.features.login.ui.viewmodel

import com.ghrisav.commonandroid.ui.viewmodel.BaseViewModel
import com.ghrisav.features.login.ui.fragment.LoginFragmentDirections

class LoginViewModel: BaseViewModel() {
    fun navigateToHome() {
        navigate(LoginFragmentDirections.goToHome())
    }
}