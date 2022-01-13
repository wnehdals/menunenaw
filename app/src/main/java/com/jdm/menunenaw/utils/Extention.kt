package com.jdm.menunenaw.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.controlSoftKeyboard(view: View, isShow : Boolean){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if(isShow){
        imm.showSoftInput(view,0)
    }else{
        imm.hideSoftInputFromWindow(view.windowToken,0)
    }
}
