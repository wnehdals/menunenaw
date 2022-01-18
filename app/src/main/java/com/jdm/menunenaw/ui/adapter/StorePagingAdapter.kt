package com.jdm.menunenaw.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import com.jdm.menunenaw.databinding.ItemStoreInfoBinding
import com.jdm.menunenaw.utils.DiffUtilCallback

class StorePagingAdapter :
    PagingDataAdapter<CategorySearchResponse.Document, StorePagingAdapter.StoreViewHolder>(storeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_store_info,
            parent,
            false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        getItem(position)?.let{
            with(holder.binding){
                tvStoreInfoTitle.text = it.place_name
                ivStoreInfoSelect.isSelected = it.select ?: true
                distance = "${it.distance}m"
                category = it.category_name.substring(
                    0.coerceAtLeast(it.category_name.lastIndexOf('>') + 1),
                    it.category_name.length
                )
            }
            /*이미지가 없음.*/
            /*Glide.with(holder.binding.root.context)
                .load(it.place_url)
                .into(holder.binding.ivStoreInfoImg)*/
        }
    }

    inner class StoreViewHolder(val binding:ItemStoreInfoBinding) : RecyclerView.ViewHolder(binding.root){

    }

    companion object{
        val storeDiffUtil = DiffUtilCallback<CategorySearchResponse.Document>(calSame = {
            it.first.id == it.second.id
        })
    }
}