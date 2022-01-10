package com.jdm.menunenaw.ui.main

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.jdm.menunenaw.base.ViewBindingActivity
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import android.content.pm.PackageInfo
import androidx.navigation.ui.setupWithNavController
import java.security.NoSuchAlgorithmException


@AndroidEntryPoint
class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    private val TAG = MainActivity::class.java.simpleName
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun subscribe() {

    }

    override fun initView() {
        setupToolbar()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setupToolbar() {
        binding.tbMain.let { toolbar ->
            setSupportActionBar(toolbar)
            val navController =
                (supportFragmentManager.findFragmentById(R.id.fcv_nav_main_container) as NavHostFragment).navController
            val appConfig = AppBarConfiguration(navController.graph)
            toolbar.setupWithNavController(navController, appConfig)
            supportActionBar?.title = ""
        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e(TAG, "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e(TAG, "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}