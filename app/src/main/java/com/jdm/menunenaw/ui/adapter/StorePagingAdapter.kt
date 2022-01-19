package com.jdm.menunenaw.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import com.jdm.menunenaw.databinding.ItemStoreInfoBinding
import com.jdm.menunenaw.utils.DiffUtilCallback
import com.jdm.menunenaw.utils.select

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
            holder.bind(it)
        }
    }

    inner class StoreViewHolder(val binding:ItemStoreInfoBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(document: CategorySearchResponse.Document){
            with(binding){
                this.document = document
                document.selectChangeListener = {
                    this.ivStoreInfoSelect.select = document.select
                }
                /*이미지가 없음.*/
                /*Glide.with(holder.binding.root.context)
                    .load(it.place_url)
                    .into(holder.binding.ivStoreInfoImg)*/
            }
        }
    }

    companion object{
        val storeDiffUtil = DiffUtilCallback<CategorySearchResponse.Document>(calSame = {
            it.first.id == it.second.id &&  it.first.select == it.second.select
        })
    }
}