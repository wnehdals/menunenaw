package com.jdm.menunenaw.ui.category

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.data.DEFAULT_CIRCLE_RADIUS
import com.jdm.menunenaw.data.DEFAULT_LATITUDE
import com.jdm.menunenaw.data.DEFAULT_LONGITUDE
import com.jdm.menunenaw.databinding.FragmentStoreSelectBinding
import com.jdm.menunenaw.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StoreSelectFragment : ViewBindingFragment<FragmentStoreSelectBinding>() {

    override val layoutId: Int = R.layout.fragment_store_select
    private val viewModel : MainViewModel by activityViewModels()

    private var locationLatitude = DEFAULT_LATITUDE
    private var locationLongitude = DEFAULT_LONGITUDE
    private var radius = DEFAULT_CIRCLE_RADIUS

    override fun initView() {
        super.initView()
        initData()
    }

    override fun subscribe() {
//        viewModel.searchResult.observe(this){}
    }

    private fun initData(){
        arguments?.getDouble(BundleKey.LOCATION_Y.name)?.let{ locationLatitude = it }
        arguments?.getDouble(BundleKey.LOCATION_X.name)?.let{ locationLongitude = it }
        arguments?.getInt(BundleKey.RADIUS.name)?.let{ radius = it }
    }
}