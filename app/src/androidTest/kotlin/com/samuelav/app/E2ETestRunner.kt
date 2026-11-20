package com.samuelav.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class E2ETestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application =
        super.newApplication(cl, E2ETestApp::class.java.name, context)
}
