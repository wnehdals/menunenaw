package com.jdm.menunenaw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.data.remote.response.LocationSearchResponse.Document
import com.jdm.menunenaw.databinding.ItemLocationSearchBinding


class LocationSearchListAdapter(private val itemClick: (Document) -> Unit) : ListAdapter<Document,LocationSearchListAdapter.LocationViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_location_search,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class LocationViewHolder(private val binding : ItemLocationSearchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : Document){
            binding.tvLocationAddress.text = data.address_name
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    companion object{
        val diffUtil = object :DiffUtil.ItemCallback<Document>(){
            override fun areItemsTheSame(
                oldItem: Document,
                newItem: Document
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Document,
                newItem: Document
            ): Boolean {
                return oldItem.address == newItem.address
            }
        }
    }
}