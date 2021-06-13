package com.ghrisav.commonandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ghrisav.commonandroid.extensions.setUpSnackbar
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment(), BaseFragmentContract {

    /* Abstract methods */
    abstract fun onViewCreatedFragment()

    /* Open methods */
    open fun onCreateFragment() { /* No-op */ }

    /* Override methods */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onCreateBinding(inflater, container, savedInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation(viewLifecycleOwner, getViewModel(), findNavController())
        observeLoadingFullScreen(viewLifecycleOwner, getViewModel(), this::setFullScreenLoading)
        setUpSnackbar(this, getViewModel().isSnackBarError(), Snackbar.LENGTH_LONG)
        onViewCreatedFragment()
    }

    /* Public methods */
    fun showToolbar() = applyIntoBaseActivity(activity) { it.showToolbar() }
    fun showBottomNavigation() = applyIntoBaseActivity(activity) { it.showBottomNavigation() }
    fun hideToolbar() = applyIntoBaseActivity(activity) { it.hideToolbar() }
    fun hideBottomNavigation() = applyIntoBaseActivity(activity) { it.hideBottomNavigation() }

    fun setOnBackPressedCallback(isEnabled: Boolean = true, handleOnBackPressed: () -> Unit) {
        val onBackPressedCallback = object : OnBackPressedCallback(isEnabled) {
            override fun handleOnBackPressed() {
                handleOnBackPressed()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    /* Private methods */
    private fun setFullScreenLoading(visible: Boolean) = applyIntoBaseActivity(activity) {
        it.setFullScreenLoading(visible)
    }
}