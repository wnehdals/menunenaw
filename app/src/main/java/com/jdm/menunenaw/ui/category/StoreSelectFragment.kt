package com.jdm.menunenaw.ui.category

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.map
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
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StoreSelectFragment : ViewBindingFragment<FragmentStoreSelectBinding>() {

    override val layoutId: Int = R.layout.fragment_store_select
    private val viewModel : MainViewModel by activityViewModels()
    private val storeAdapter by lazy{StorePagingAdapter()}

    private var locationLatitude = DEFAULT_LATITUDE
    private var locationLongitude = DEFAULT_LONGITUDE
    private var radius = DEFAULT_CIRCLE_RADIUS

    val allSelectLiveData = MutableLiveData(true)
    private val selectList = arrayListOf<String>()
    private val unSelectList = arrayListOf<String>()
    private var currentSelectMode = true // true : allSelect, false : allUnSelect

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
        lifecycleScope.launch {
            viewModel.getStoreList(locationLatitude,locationLongitude,radius).collectLatest {
                withContext(Dispatchers.IO){
                    storeAdapter.submitData(it.map {  document ->
                        if(currentSelectMode){
                            document.select = !unSelectList.contains(document.id)
                        } else {
                            document.select = selectList.contains(document.id)
                        }
                        document
                    })
                }
            }
        }
    }

    private fun initData(){
        arguments?.getDouble(BundleKey.LOCATION_Y.name)?.let{ locationLatitude = it }
        arguments?.getDouble(BundleKey.LOCATION_X.name)?.let{ locationLongitude = it }
        arguments?.getInt(BundleKey.RADIUS.name)?.let{ radius = it }
    }

    private fun resetAndSumitData(){
        val snapshot = storeAdapter.snapshot()
        snapshot.forEach {  document ->
            document?.updateSelect(currentSelectMode)
        }
    }

    fun onClickOfAllChecked(){
        allSelectLiveData.value = !allSelectLiveData.value!!
        currentSelectMode = allSelectLiveData.value!!
        unSelectList.clear()
        selectList.clear()
        resetAndSumitData()
    }
}