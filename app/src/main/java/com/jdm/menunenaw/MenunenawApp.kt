package com.jdm.menunenaw

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.kakao.util.maps.helper.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MenunenawApp: Application() {
    val appContext: Context = this
    val isApplicationDebug
        get() = isApplicationDebug(appContext)
    lateinit var kakaoAppKey: String
    override fun onCreate() {
        super.onCreate()
        instance = this
        kakaoAppKey = this.resources.getString(R.string.KAKAO_HEADER_KEY)
    }
    /**
     * 디버그모드인지 확인하는 함수
     */
    private fun isApplicationDebug(context: Context): Boolean {
        var debuggable = false
        val pm: PackageManager = context.packageManager
        try {
            val appinfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != appinfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
        }

        return debuggable
    }
    companion object {
        lateinit var instance: MenunenawApp
            private set
    }
}