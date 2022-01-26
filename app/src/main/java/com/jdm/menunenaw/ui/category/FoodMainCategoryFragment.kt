package com.jdm.menunenaw.ui.category

import android.util.Log
import androidx.fragment.app.viewModels
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.model.FoodCategory
import com.jdm.menunenaw.databinding.FragmentFoodLargeCategoryBinding
import com.jdm.menunenaw.ui.adapter.FoodMainCategoryAdapter
import com.jdm.menunenaw.ui.main.MainActivity
import com.jdm.menunenaw.vm.FoodCategorySelectViewModel
import com.jdm.menunenaw.vm.StoreSelectViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FoodMainCategoryFragment : ViewBindingFragment<FragmentFoodLargeCategoryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_food_large_category

    private val foodCategorySelectViewModel : FoodCategorySelectViewModel by viewModels()

    private val adapter by lazy { FoodMainCategoryAdapter().apply {
        submitList(getCategoryData())
    }}

    override fun subscribe() {

    }

    override fun initView() {
        (activity as? MainActivity)?.enableActionBar(false)
        binding.fragment = this@FoodMainCategoryFragment
        binding.rvFoodMainCategory.adapter = adapter
    }

    private fun getCategoryData(): List<FoodCategory> {
        val titleCategory = resources.getStringArray(R.array.food_category)
        val arrayId = listOf(R.array.koreanFood, R.array.americanFood, R.array.chineseFood, R.array.japaneseFood)
        val list = mutableListOf<FoodCategory>()
        for (i in titleCategory.indices) {
            list.add(
                FoodCategory(
                    mainCategoryTitle = titleCategory[i],
                    subCategoryList = resources.getStringArray(arrayId[i])
                )
            )
        }
        return list
    }

    fun onClickNext() {
        // Todo: 룰렛에 데이터 넘겨줘야 함.
        val data = foodCategorySelectViewModel.makeRouletteDataArray(adapter.currentList)
        Log.i("eunjin", "data ${data.contentToString()}")
    }

    override fun onDestroy() {
        (activity as? MainActivity)?.enableActionBar(true)
        super.onDestroy()
    }
}