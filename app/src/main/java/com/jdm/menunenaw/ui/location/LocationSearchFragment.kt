package com.jdm.menunenaw.ui.location

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.data.remote.response.LocationSearchResponse
import com.jdm.menunenaw.databinding.FragmentLocationSearchBinding
import com.jdm.menunenaw.ui.adapter.LocationSearchListAdapter
import com.jdm.menunenaw.utils.controlSoftKeyboard
import com.jdm.menunenaw.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class LocationSearchFragment : ViewBindingFragment<FragmentLocationSearchBinding>() {
    private val TAG = LocationSearchFragment::class.java.simpleName
    override val layoutId: Int = R.layout.fragment_location_search
    private val viewModel: MainViewModel by activityViewModels()
    private val locationSearchAdapter by lazy { LocationSearchListAdapter(itemClick = {item -> onClickOfLocationItem(item)}) }

    override fun initView() {
        binding.apply {
            fragment = this@LocationSearchFragment
            tvLocationSearchResultList.adapter = locationSearchAdapter
            svLocationSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.queryFlow.value = newText
                    return false
                }
            })
        }
    }

    override fun subscribe() {
        viewModel.searchResult.observe(this) { pair ->
            binding.run {
                if (pair.first == MainViewModel.DataType.REMOTE) {
                    tvLocationSearchRecent.visibility = View.GONE
                    locationSearchAdapter.submitList(pair.second)
                } else {
                    tvLocationSearchRecent.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onClickOfLocationItem(item : LocationSearchResponse.Document){
        Log.i(TAG,"onClickOfLocationItem : $item ")
        binding.let{ context?.controlSoftKeyboard(it.root,false) }
        moveFragment(R.id.action_locationSearchFragment_to_mapBoundFragment
            ,bundle = Bundle().apply {
                putString(BundleKey.LOCATION_Y.name, item.y)
                putString(BundleKey.LOCATION_X.name, item.x)
                putString(BundleKey.LOCATION_NAME.name,item.address_name)
            })
    }
}