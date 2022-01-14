package com.jdm.menunenaw.ui.roulette

import android.util.Log
import com.jdm.menunenaw.base.ViewBindingFragment
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.FragmentRouletteBinding
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.jdm.menunenaw.data.BundleKey
import com.jdm.menunenaw.ui.roulette.custom.RouletteListener
import com.jdm.menunenaw.vm.RouletteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouletteFragment: ViewBindingFragment<FragmentRouletteBinding>() {
    private val TAG = RouletteFragment::class.java.simpleName

    override val layoutId = R.layout.fragment_roulette
    private val viewModel: RouletteViewModel by activityViewModels()

    private val rouletteView by lazy { binding.rouletteView }

    override fun subscribe() {
        viewModel.currentLabel.observe(viewLifecycleOwner, {
            binding.tvResult.text = it
        })
    }

    override fun initView() {
        binding.button.setOnClickListener {
            rouletteView.startRouletteRotation()
        }
        initRouletteView()
    }

    private fun initRouletteView() {
        initRouletteBottomMargin()
        rouletteView.setRouletteData(
            arguments?.getStringArrayList(BundleKey.ROULETTE_DATA_LIST.name)?.toList() ?: listOf("데이터는", "최소", "3개")
        )
        rouletteView.setOnRouletteResultListener(object : RouletteListener {
            override fun getRotateEndResult(result: String) {
                Toast.makeText(requireContext(), "$result 당첨", Toast.LENGTH_LONG).show()
            }
            override fun getRotatingResult(data: String) {
                viewModel.setCurrentLabel(data)
            }
        })
    }

    /** rouletteView 의 width, Bottom Margin 설정 */
    private fun initRouletteBottomMargin() {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val rouletteViewParams: ConstraintLayout.LayoutParams = rouletteView.layoutParams as  ConstraintLayout.LayoutParams
                rouletteViewParams.width = binding.root.height
                rouletteViewParams.setMargins(0, 0, 0, -(binding.root.height/2))
                rouletteView.layoutParams = rouletteViewParams
            }
        })
    }
}