package com.jdm.menunenaw.ui.main

import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.databinding.FragmentMainBinding

class MainFragment : ViewBindingFragment<FragmentMainBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun subscribe() {}
}