package com.jdm.menunenaw.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse.Document
import com.jdm.menunenaw.databinding.ItemStoreInfoBinding
import com.jdm.menunenaw.utils.select

class StorePagingAdapter(val itemClick: (Document) -> Unit) :
    RecyclerView.Adapter<StorePagingAdapter.StoreViewHolder>() {
    var list: List<Document> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_store_info,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        try {
            holder.bind(list[position])
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class StoreViewHolder(val binding: ItemStoreInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(document: Document) {
            with(binding) {
                this.document = document
                document.updateListener = { this.ivStoreInfoSelect.select = document.select }
                clStoreInfoContainer.setOnClickListener { itemClick(document) }
                chipStoreInfoSubDetail.setOnClickListener {
                    try {
                        binding.root.context.startActivity(
                            Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(document.place_url) }
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}