package com.jdm.menunenaw.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ViewBindingActivity<T : ViewDataBinding> : ActivityBase() {
    @get:LayoutRes abstract  val layoutId: Int
    private var _binding: T? = null
    val binding: T?
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.unbind()
    }

    open fun initView() = Unit
    abstract fun subscribe()
    open fun initState() {
        initView()
        subscribe()
    }
}