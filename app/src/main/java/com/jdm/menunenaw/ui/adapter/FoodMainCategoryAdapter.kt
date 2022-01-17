package com.jdm.menunenaw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.chip.Chip
import com.jdm.menunenaw.data.model.FoodCategory
import com.jdm.menunenaw.databinding.ItemFoodMainCategoryBinding
import com.jdm.menunenaw.utils.DiffUtilCallback
import com.jdm.menunenaw.R


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
        fun bind(data : FoodCategory) {
            binding.root.setOnClickListener {
                it.isSelected = !it.isSelected
                binding.isSelected = it.isSelected
            }
            binding.tvTitle.text = data.mainCategoryTitle
            binding.imgPhoto.run {
                Glide.with(context)
                    .load(R.drawable.korea)
                    .transform(CenterCrop(), RoundedCorners(10))
                    .into(this)
            }
            binding.chipGroupSubCategory.let { group ->
                data.subCategoryList.forEach {
                    val newChip = Chip(group.context).apply {
                        text = it
                    }
                    group.addView(newChip)
                }
            }
        }
    }

    companion object{
        val diffUtil = DiffUtilCallback<FoodCategory>(calSame = {
            it.first.mainCategoryTitle == it.second.mainCategoryTitle
        })
    }
}
