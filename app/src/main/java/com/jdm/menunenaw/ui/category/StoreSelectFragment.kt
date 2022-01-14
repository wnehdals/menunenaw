package com.jdm.menunenaw.ui.category

import androidx.fragment.app.activityViewModels
import com.jdm.menunenaw.R
import com.jdm.menunenaw.base.ViewBindingFragment
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

    override fun initView() {
        super.initView()
    }

    override fun subscribe() {
//        viewModel.searchResult.observe(this){}
    }
}