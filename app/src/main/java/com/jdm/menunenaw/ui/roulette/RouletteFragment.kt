package com.jdm.menunenaw.ui.roulette


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.FragmentRouletteBinding
import androidx.constraintlayout.widget.ConstraintLayout

import android.view.ViewTreeObserver.OnGlobalLayoutListener


class RouletteFragment: ViewBindingFragment<FragmentRouletteBinding>() {

    companion object {
        val BUNDLE_DATA_ROULETTE_LIST = "rouletteList"
    }

    override val layoutId = R.layout.fragment_roulette

    private val rouletteView by lazy { binding.rouletteView }

    override fun subscribe() {
        mutableLiveData.observe(viewLifecycleOwner, {
            binding.tvResult.text = it
        })
    }

    private val mutableLiveData = MutableLiveData<String>()

    override fun initView() {
        binding.button.setOnClickListener {
            rouletteView.startRouletteRotation()
        }
        initRouletteView()
    }

    private fun initRouletteView() {
        initRouletteBottomMargin()
        rouletteView.setRouletteData(
            arguments?.getStringArrayList(BUNDLE_DATA_ROULETTE_LIST)?.toList() ?: listOf("데이터는", "최소", "3개")
        )
        rouletteView.setOnRouletteResultListener(mutableLiveData) {
            Log.i("eunjin", "result $it")
        }
    }

    /** rouletteView 의 width/2 만큼 Bottom Margin 설정 */
    private fun initRouletteBottomMargin() {
        rouletteView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rouletteView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                // get width and height of the view
                val params: ConstraintLayout.LayoutParams = rouletteView.layoutParams as  ConstraintLayout.LayoutParams
                params.setMargins(0, 0, 0, -(rouletteView.width/2))
                rouletteView.layoutParams = params
            }
        })
    }
}