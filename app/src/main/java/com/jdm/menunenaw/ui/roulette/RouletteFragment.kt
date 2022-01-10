package com.jdm.menunenaw.ui.roulette


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.FragmentRouletteBinding

class RouletteFragment: ViewBindingFragment<FragmentRouletteBinding>() {

    override val layoutId = R.layout.fragment_roulette

    private val rouletteView by lazy { binding.rouletteView }

    override fun subscribe() {
        mutableLiveData.observe(viewLifecycleOwner, {
            binding.tvResult.text = it
        })
    }

    private val mutableLiveData = MutableLiveData<String>()

    override fun initView() {
        binding.button.setOnClickListener { rouletteView.startRoulette(mutableLiveData) {
            Log.i("eunjin", "result $it")
        }
        }
    }
}