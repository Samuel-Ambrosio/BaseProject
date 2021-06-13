package com.ghrisav.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ghrisav.app.R
import com.ghrisav.app.ui.viewmodel.SplashViewModel
import com.ghrisav.common.utils.DELAY_SPLASH
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity() {

    private val splashViewModel by viewModel<SplashViewModel>()

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startApp()
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    private fun startApp() {
        activityScope.launch {
            delay(DELAY_SPLASH)
            val intent = Intent(this@SplashActivity, NavHostActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}