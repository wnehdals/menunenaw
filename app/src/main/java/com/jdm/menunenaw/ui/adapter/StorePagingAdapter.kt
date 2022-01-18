package com.jdm.menunenaw.ui.adapter

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

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        getItem(position)?.let{
            holder.binding.tvStoreInfoName.text = it.place_name
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