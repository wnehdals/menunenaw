package com.jdm.menunenaw.ui.category

import androidx.fragment.app.Fragment
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.model.FoodCategory
import com.jdm.menunenaw.databinding.FragmentFoodLargeCategoryBinding
import com.jdm.menunenaw.ui.adapter.FoodMainCategoryAdapter
import com.jdm.menunenaw.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodMainCategoryFragment : ViewBindingFragment<FragmentFoodLargeCategoryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_food_large_category

    private val adapter = FoodMainCategoryAdapter()

    override fun subscribe() {

    }

    override fun initView() {
        (activity as? MainActivity)?.enableActionBar(false)
        binding.rvFoodMainCategory.adapter = adapter
        adapter.submitList(listOf(
            FoodCategory(mainCategoryTitle = "한식", subCategoryList = resources.getStringArray(R.array.korea)),
            FoodCategory(mainCategoryTitle = "일식", subCategoryList = resources.getStringArray(R.array.japan)),
            FoodCategory(mainCategoryTitle = "중식", subCategoryList = resources.getStringArray(R.array.china)),
            FoodCategory(mainCategoryTitle = "양식", subCategoryList = resources.getStringArray(R.array.america))
        ))
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.enableActionBar(true)
        super.onDestroy()
    }
}