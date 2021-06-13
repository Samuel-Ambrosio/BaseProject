package com.ghrisav.commonandroid.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.ghrisav.commonandroid.ui.activity.BaseActivity
import com.ghrisav.commonandroid.ui.viewmodel.BaseViewModel
import com.ghrisav.navigation.NavigationCommand

internal interface BaseFragmentContract {

    fun observeNavigation(
        viewLifecycleOwner: LifecycleOwner,
        viewModel: BaseViewModel,
        navController: NavController
    ) {
        viewModel.getNavigation().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> navController.navigate(
                        command.directions,
                        getExtras()
                    )
                    is NavigationCommand.Back -> navController.navigateUp()
                }
            }
        })
    }

    fun observeLoadingFullScreen(
        viewLifecycleOwner: LifecycleOwner,
        viewModel: BaseViewModel,
        setLoading: (Boolean) -> Unit
    ) {
        viewModel.isLoadingFullScreen().observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { isLoading ->
                setLoading(isLoading)
            }
        })
    }

    fun applyIntoBaseActivity(activity: Activity?, action: (baseActivity: BaseActivity) -> Unit) {
        if (activity != null && activity is BaseActivity) {
            action(activity)
        }
    }

    fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    fun getViewModel(): BaseViewModel

    fun onCreateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
}