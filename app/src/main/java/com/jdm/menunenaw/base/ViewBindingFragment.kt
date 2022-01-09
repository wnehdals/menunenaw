package com.jdm.menunenaw.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

abstract class ViewBindingFragment<T : ViewDataBinding> : BaseFragment() {
    @get:LayoutRes
    abstract val layoutId: Int
    private  var _binding: T? = null
    val binding: T?
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        _binding!!.lifecycleOwner = this
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }
    open fun initView() = Unit
    abstract fun subscribe()
    open fun initState() {
        initView()
        subscribe()
    }

    fun moveFragment(@IdRes id: Int, bundle: Bundle? = null){
        try{
            findNavController().navigate(id,bundle)
        } catch(e:Exception){
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        binding?.unbind()
        super.onDestroy()
    }
}
