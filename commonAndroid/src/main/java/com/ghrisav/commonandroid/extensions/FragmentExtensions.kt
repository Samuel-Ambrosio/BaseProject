package com.ghrisav.commonandroid.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.ghrisav.common.utils.Event
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_LONG) {
    activity.getIfActive()?.let {
        Snackbar.make(it.findViewById(android.R.id.content), snackbarText, timeLength).show()
    }
}

fun Fragment.setUpSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<String>>,
    timeLength: Int = Snackbar.LENGTH_LONG
) {
    snackbarEvent.observe(lifecycleOwner, { event ->
        event.getContentIfNotHandled()?.let { text ->
            context?.let { showSnackbar(text, timeLength) }
        }
    })
}