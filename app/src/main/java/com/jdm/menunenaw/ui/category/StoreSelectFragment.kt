package com.jdm.menunenaw.ui.category

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.data.DEFAULT_CIRCLE_RADIUS
import com.jdm.menunenaw.data.DEFAULT_LATITUDE
import com.jdm.menunenaw.data.DEFAULT_LONGITUDE
import com.jdm.menunenaw.data.remote.response.CategorySearchResponse
import com.jdm.menunenaw.databinding.FragmentStoreSelectBinding
import com.jdm.menunenaw.ui.adapter.StorePagingAdapter
import com.jdm.menunenaw.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StoreSelectFragment : ViewBindingFragment<FragmentStoreSelectBinding>() {

    override val layoutId: Int = R.layout.fragment_store_select
    private val viewModel : MainViewModel by activityViewModels()
    private val storeAdapter by lazy{StorePagingAdapter{ onClickOfListItem(it) } }

    private var locationLatitude = DEFAULT_LATITUDE
    private var locationLongitude = DEFAULT_LONGITUDE
    private var radius = DEFAULT_CIRCLE_RADIUS

    val allSelectLiveData = MutableLiveData(true)

    override fun initView() {
        initData()
        binding.apply {
            fragment = this@StoreSelectFragment
            lifecycleOwner = this@StoreSelectFragment

            rvStoreSelectList.adapter = storeAdapter
            rvStoreSelectList.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    override fun subscribe() {
        viewModel.searchStoreResult.observe(this){
            it.forEach { it.select = allSelectLiveData.value!! }
            storeAdapter.submitList(it)
        }
    }

    private fun initData(){
        arguments?.getDouble(BundleKey.LOCATION_Y.name)?.let{ locationLatitude = it }
        arguments?.getDouble(BundleKey.LOCATION_X.name)?.let{ locationLongitude = it }
        arguments?.getInt(BundleKey.RADIUS.name)?.let{ radius = it }
    }

    private fun reInitListItem(){
        /* todo: update 방법 고민.*/
    }

    fun onClickOfAllChecked(){
        allSelectLiveData.value = !allSelectLiveData.value!!
        reInitListItem()
    }

    private fun onClickOfListItem(document : CategorySearchResponse.Document){

    }
}