package com.jdm.menunenaw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.model.FoodCategory
import com.jdm.menunenaw.databinding.ItemFoodMainCategoryBinding
import com.jdm.menunenaw.utils.DiffUtilCallback
import dagger.hilt.android.AndroidEntryPoint


class FoodMainCategoryAdapter: ListAdapter<FoodCategory, FoodMainCategoryAdapter.FoodLargeCategoryViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodLargeCategoryViewHolder {
        return FoodLargeCategoryViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_food_main_category,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FoodLargeCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodLargeCategoryViewHolder(private val binding : ItemFoodMainCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : FoodCategory){
            binding.tvTitleMainCategory.text = data.mainCategoryTitle
        }
    }

    companion object{
        val diffUtil = DiffUtilCallback<FoodCategory>(calSame = {
            it.first.mainCategoryTitle == it.second.mainCategoryTitle
        })
    }
}
