package com.jdm.garam.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class ViewBindingActivity<T : ViewDataBinding> : ActivityBase() {
    @get:LayoutRes abstract  val layoutId: Int
    private lateinit var _binding: T
    val binding: T
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    open fun initView() = Unit
    abstract fun subscribe()
    open fun initState() {
        initView()
        subscribe()
    }
}