package com.ghrisav.commonandroid.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.ghrisav.common.extensions.TAG
import com.ghrisav.commonandroid.R
import com.ghrisav.commonandroid.extensions.setUpSnackbar
import com.google.android.material.snackbar.Snackbar

abstract class BaseDialogFragment : DialogFragment(), BaseFragmentContract {

    companion object {
        const val WRAP_CONTENT = -1f
        const val MATCH_PARENT = 1f
    }

    /* Abstract methods */
    abstract fun displayRectangleWidthFactor(): Float

    abstract fun displayRectangleHeightFactor(): Float

    abstract fun onViewCreatedDialogFragment()

    /* Open methods */
    open fun onCreateDialogFragment() { /* No-op */ }

    /* Override methods */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Base_Theme_MaterialComponents_Dialog)
        onCreateDialogFragment()
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
        onViewCreatedDialogFragment()
    }

    override fun onStart() {
        super.onStart()
        setUpWindow()
    }

    /* Private methods */
    private fun setUpWindow() {
        try {
            val displayRectangle = Rect()
            dialog?.window?.apply {
                decorView.getWindowVisibleDisplayFrame(displayRectangle)
                setLayout(
                    setUpLayoutMeasure(displayRectangle.width(), displayRectangleWidthFactor()),
                    setUpLayoutMeasure(displayRectangle.height(), displayRectangleHeightFactor())
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Error setting up window.")
        }
    }

    private fun setUpLayoutMeasure(layoutMeasure: Int, layoutFactor: Float): Int {
        return if (layoutFactor == WRAP_CONTENT) {
            WindowManager.LayoutParams.WRAP_CONTENT
        } else {
            (layoutMeasure * layoutFactor).toInt()
        }
    }

    private fun setFullScreenLoading(visible: Boolean) = applyIntoBaseActivity(activity) {
        it.setFullScreenLoading(visible)
    }
}