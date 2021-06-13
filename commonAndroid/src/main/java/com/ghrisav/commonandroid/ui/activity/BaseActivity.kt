package com.ghrisav.commonandroid.ui.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ghrisav.commonandroid.R
import com.ghrisav.commonandroid.databinding.ActivityBaseBinding
import com.ghrisav.commonandroid.extensions.doGone
import com.ghrisav.commonandroid.extensions.doVisible
import com.ghrisav.commonandroid.extensions.doVisibleOrGone

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNavigation()
    }

    /* Public methods */
    fun setFullScreenLoading(visible: Boolean) = binding.baseIncludeLoading.root.doVisibleOrGone { visible }
    fun showToolbar() = binding.baseIncludeToolbar.root.doVisible()
    fun showBottomNavigation() = binding.baseViewBottomNav.doVisible()
    fun hideToolbar() = binding.baseIncludeToolbar.root.doGone()
    fun hideBottomNavigation() = binding.baseViewBottomNav.doGone()

    /* Private methods */
    private fun setUpBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.base__host__fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.baseViewBottomNav, navController)
    }
}