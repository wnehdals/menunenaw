package com.jdm.menunenaw.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.databinding.FragmentFoodLargeCategoryBinding
import com.jdm.menunenaw.ui.main.MainActivity


/**
 * A simple [Fragment] subclass.
 * Use the [FoodLargeCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodLargeCategoryFragment : ViewBindingFragment<FragmentFoodLargeCategoryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_food_large_category

    override fun subscribe() {

    }

    override fun initView() {
        //(activity as (MainActivity)).setBaseAppBar(getString(R.string.food_roulette))
        //(activity as (MainActivity)).setBackButtonAppBar()
    }
}