package com.jdm.menunenaw.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import com.jdm.menunenaw.databinding.ItemStoreInfoBinding

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

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        getItem(position)?.let{
            holder.binding.tvStoreInfoName.text = it.place_name
        }
    }

    inner class StoreViewHolder(val binding:ItemStoreInfoBinding) : RecyclerView.ViewHolder(binding.root){

    }
    companion object{
        /* todo : develop에 diffutil 제네릭ver 머지되면 수정 */
        val storeDiffUtil = object : DiffUtil.ItemCallback<CategorySearchResponse.Document>(){
            override fun areItemsTheSame(
                oldItem: CategorySearchResponse.Document,
                newItem: CategorySearchResponse.Document
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategorySearchResponse.Document,
                newItem: CategorySearchResponse.Document
            ): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.category_group_code == newItem.category_group_code
                        && oldItem.y == newItem.y
                        && oldItem.x == newItem.x
            }
        }
    }
}