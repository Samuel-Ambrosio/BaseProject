package com.ghrisav.features.login.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ghrisav.commonandroid.extensions.setOnSingleClickListener
import com.ghrisav.commonandroid.ui.fragment.BaseFragment
import com.ghrisav.commonandroid.ui.viewmodel.BaseViewModel
import com.ghrisav.features.login.databinding.FragmentLoginBinding
import com.ghrisav.features.login.ui.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment() {

    private val loginViewModel by viewModel<LoginViewModel>()

    private lateinit var binding: FragmentLoginBinding

    override fun getViewModel(): BaseViewModel = loginViewModel

    override fun onCreateFragment() {

    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onViewCreatedFragment() {
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        binding.fragmentLoginBtnGoToHome.setOnSingleClickListener { loginViewModel.navigateToHome() }
    }

    private fun setUpObservers() {

    }
}