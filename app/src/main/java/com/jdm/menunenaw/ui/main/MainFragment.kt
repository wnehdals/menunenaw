package com.jdm.menunenaw.ui.main

import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : ViewBindingFragment<FragmentMainBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun initView() {
        super.initView()
        binding.fragment = this
    }

    override fun subscribe() {

    }

    fun onClickOfFoodRoulette(){
        moveFragment(R.id.action_mainFragment_to_locationSearchFragment)
    }
}