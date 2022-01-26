package com.jdm.menunenaw.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEach
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
        @SuppressLint("ResourceType")
        fun bind(data : FoodCategory) {
            binding.run {
                clAlways.setOnClickListener {
                    it.isSelected = !it.isSelected
                    if (it.isSelected) {
                        mlCategory.transitionToEnd()
                    } else {
                        mlCategory.transitionToStart()
                    }
                }
                tvTitle.text = data.mainCategoryTitle
                tvCount.apply {
                    text = resources.getString(R.string.select_count_text, currentList[bindingAdapterPosition].selectedList?.count { it })
                }
                imgPhoto.run {
                    Glide.with(context)
                        .load(R.drawable.korea)
                        .transform(CenterCrop(), RoundedCorners(10))
                        .into(this)
                }
                cbTotal.setOnCheckedChangeListener { view, isChecked ->
                    when {
                        view.isPressed -> {
                            chipGroupSubCategory.forEach {
                                (it as Chip).isChecked = isChecked
                            }
                        }
                    }
                }
                chipGroupSubCategory.let { group ->
                    data.subCategoryList.forEachIndexed { index, item ->
                        val newChip = Chip(group.context).apply {
                            text = item
                            isClickable = true
                            isCheckable = true
                            checkedIcon = null
                            isChecked = data.selectedList?.get(index) ?: true
                            setChipBackgroundColorResource(R.drawable.chip_bg_states)
                            setOnCheckedChangeListener { _, isChecked ->
                                currentList[bindingAdapterPosition].selectedList?.set(index, isChecked)
                                val currentSelectCount = currentList[bindingAdapterPosition].selectedList?.count { it }
                                tvCount.text = resources.getString(R.string.select_count_text, currentSelectCount)
                                cbTotal.isChecked = currentSelectCount == currentList[bindingAdapterPosition].selectedList?.size
                            }
                        }
                        group.addView(newChip)
                    }
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
