package com.jdm.menunenaw.ui.main

import android.annotation.SuppressLint
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.jdm.menunenaw.base.ViewBindingActivity
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun subscribe() {
        
    }

    override fun initView() {
        setupToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupToolbar(){
        binding?.tbMain?.let { toolbar ->
            setSupportActionBar(toolbar)

            val navController =
                (supportFragmentManager.findFragmentById(R.id.fcv_nav_main_container) as NavHostFragment).navController
            val appConfig = AppBarConfiguration(navController.graph)
            toolbar.setupWithNavController(navController, appConfig)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if(destination.id == R.id.mainFragment){
                    supportActionBar?.hide()
                }else{
                    supportActionBar?.show()
                    toolbar.navigationIcon = getDrawable(R.drawable.ic_chevron_left)
                }
            }
        }
    }
}