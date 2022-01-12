package com.jdm.menunenaw.utils

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat

fun Context.controlSoftKeyboard(view: View, isShow: Boolean) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (isShow) {
        imm.showSoftInput(view, 0)
    } else {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Context.checkPermissionsAndRequest(permissions: Array<String>, request: ActivityResultLauncher<Array<String>>) : Boolean{
    val result = hasPermissions(permissions)
    if(!result){
        request.launch(permissions)
    }
    return result
}

fun Context.hasPermissions(permissions: Array<String>): Boolean =
    permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }