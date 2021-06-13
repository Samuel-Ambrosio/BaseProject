package com.ghrisav.features.home.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.ghrisav.commonandroid.ui.fragment.BaseFragment
import com.ghrisav.commonandroid.ui.viewmodel.BaseViewModel
import com.ghrisav.features.home.databinding.FragmentHomeBinding
import com.ghrisav.features.home.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

    override fun getViewModel(): BaseViewModel = homeViewModel

    override fun onCreateFragment() {
        homeViewModel.fetchAlbums()
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onViewCreatedFragment() {
        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        showToolbar()
        showBottomNavigation()
        setOnBackPressedCallback { activity?.finish() }
    }

    private fun setUpObservers() {
        homeViewModel.getAlbums().observe(viewLifecycleOwner, Observer {
            Log.wtf("Albums", it.toString())
        })
    }
}