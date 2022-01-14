package com.jdm.menunenaw.ui.main

import android.os.Bundle
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.databinding.FragmentMainBinding
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
                putStringArrayList(BundleKey.ROULETTE_DATA_LIST.name, arrayListOf("떡볶이", "아이스크림", "뼈해장국", "피자빵", "오믈렛", "맛있는파스타", "양재역점중국집", "김일도삼겹살집", "제주도에있는백종원맛있는돈까스집"))
            })
        }
    }

    fun onClickOfRestaurantRoulette(){
        moveFragment(R.id.action_mainFragment_to_locationSearchFragment)
    }
    fun onClickOfFoodRoulette() {
        moveFragment(R.id.action_mainFragment_to_foodLargeCategoryFragment)
    }
}