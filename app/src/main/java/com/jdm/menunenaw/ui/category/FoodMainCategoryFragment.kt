package com.jdm.menunenaw.ui.category

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
        val titleCategory = resources.getStringArray(R.array.food_category)
        adapter.submitList(listOf(
            FoodCategory(mainCategoryTitle = titleCategory[0], subCategoryList = resources.getStringArray(R.array.koreanFood)),
            FoodCategory(mainCategoryTitle = titleCategory[1], subCategoryList = resources.getStringArray(R.array.americanFood)),
            FoodCategory(mainCategoryTitle = titleCategory[2], subCategoryList = resources.getStringArray(R.array.chineseFood)),
            FoodCategory(mainCategoryTitle = titleCategory[3], subCategoryList = resources.getStringArray(R.array.japaneseFood))
        ))
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.enableActionBar(true)
        super.onDestroy()
    }
}