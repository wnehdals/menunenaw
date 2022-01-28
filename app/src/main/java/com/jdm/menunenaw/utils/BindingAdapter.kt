package com.jdm.menunenaw.utils

import android.view.View
import androidx.databinding.BindingAdapter

@set:BindingAdapter("bind:select")
var View.select
    get() = isSelected
    set(value) {
        isSelected = value
    }