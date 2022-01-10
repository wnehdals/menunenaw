package com.jdm.menunenaw.ui.roulette


import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.FragmentRouletteBinding
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout


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
            initRouletteBottomMargin()
        }
    }

    /** rouletteView 의 width/2 만큼 Bottom Margin 설정 */
    private fun initRouletteBottomMargin() {
        val params: ConstraintLayout.LayoutParams = rouletteView.layoutParams as  ConstraintLayout.LayoutParams
        params.setMargins(0, 0, 0, -(rouletteView.width/2))
    }
}