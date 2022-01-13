package com.jdm.menunenaw.ui.main

import android.os.Bundle
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.databinding.FragmentMainBinding
import com.jdm.menunenaw.ui.roulette.RouletteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : ViewBindingFragment<FragmentMainBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun initView() {
        super.initView()
        binding.fragment = this
        moveToRouletteFragment()
    }

    override fun subscribe() {

    }

    private fun moveToRouletteFragment() {
        //임시 버튼 생성
        binding.button2.setOnClickListener {
            moveFragment(R.id.action_mainFragment_to_rouletteFragment, bundle = Bundle().apply {
                putStringArrayList(RouletteFragment.BUNDLE_DATA_ROULETTE_LIST, arrayListOf("떡볶이", "아이스크림", "뼈해장국", "피자빵", "오믈렛", "파스타"))
            })
        }
    }

    fun onClickOfRestaurantRoulette(){
        moveFragment(R.id.action_mainFragment_to_locationSearchFragment)
    }
}