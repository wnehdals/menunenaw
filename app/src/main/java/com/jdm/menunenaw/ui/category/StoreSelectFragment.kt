package com.jdm.menunenaw.ui.category

import androidx.fragment.app.viewModels
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
import com.jdm.menunenaw.vm.StoreSelectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StoreSelectFragment : ViewBindingFragment<FragmentStoreSelectBinding>() {

    override val layoutId: Int = R.layout.fragment_store_select
    private val viewModel : StoreSelectViewModel by viewModels()
    private val storeAdapter by lazy{StorePagingAdapter{ onClickOfListItem(it) } }

    private var locationLatitude = DEFAULT_LATITUDE
    private var locationLongitude = DEFAULT_LONGITUDE
    private var radius = DEFAULT_CIRCLE_RADIUS

    override fun initView() {
        initData()
        viewModel.requestSearchCategoryAllList(locationLatitude,locationLongitude,radius)
        binding.apply {
            fragment = this@StoreSelectFragment
            viewModel = this@StoreSelectFragment.viewModel
            lifecycleOwner = this@StoreSelectFragment

            rvStoreSelectList.adapter = storeAdapter
            rvStoreSelectList.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    override fun subscribe() {
        viewModel.searchStoreResult.observe(this){
            it.forEach { it.select = viewModel.allSelectLiveData.value!! }
            storeAdapter.list = it
        }
    }

    private fun initData(){
        arguments?.getDouble(BundleKey.LOCATION_Y.name)?.let{ locationLatitude = it }
        arguments?.getDouble(BundleKey.LOCATION_X.name)?.let{ locationLongitude = it }
        arguments?.getInt(BundleKey.RADIUS.name)?.let{ radius = it }
    }

    private fun reInitListItem(){
        storeAdapter.list.forEach { it.updateSelect(viewModel.allSelectLiveData.value!!) }
        viewModel.activeNextLiveData.value = viewModel.allSelectLiveData.value
    }

    private fun updateNextButtonUi(){
        val unSelectList = storeAdapter.list.filter { !it.select }
        viewModel.allSelectLiveData.value = unSelectList.isEmpty()
        viewModel.activeNextLiveData.value = storeAdapter.list.size - unSelectList.size >= 3
    }

    fun onClickOfAllChecked(){
        viewModel.allSelectLiveData.value = !viewModel.allSelectLiveData.value!!
        reInitListItem()
    }

    private fun onClickOfListItem(document : CategorySearchResponse.Document){
        document.updateSelect(!document.select)
        updateNextButtonUi()
    }

    fun onClickOfNext(){
        val selectList = storeAdapter.list.filter { it.select }
        // todo : random 뽑기.
    }
}