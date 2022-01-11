package com.jdm.menunenaw.ui.roulette.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.LayoutRouletteViewBinding
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
class RouletteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding : LayoutRouletteViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_roulette_view,
        this,
        true
    )

    private val mSpinnerWheel: SpinnerWheel by lazy { SpinnerWheel(
        context = context,
        dataList = listOf("떡볶이", "탕수육", "라면", "돼지국밥", "케이크", "세상에서제일맛있는집", "돈까스", "육포", "미역국")
    ) }

    init {
        binding.constraintLayout.addView(mSpinnerWheel)
        setOnSwipeListener()
    }

    fun setOnRouletteResultListener(mutableLiveData: MutableLiveData<String>, endAction: (Int) -> Unit) {
        mSpinnerWheel.setOnRouletteResultListener(mutableLiveData, endAction)
    }

    fun startRouletteRotation(toDegrees: Float = (1000..2000).random().toFloat()) {
        mSpinnerWheel.rotateRoulette(toDegrees, 5000)
    }

    private fun setOnSwipeListener() {
        mSpinnerWheel.setOnTouchListener(RouletteTouchListener(context, object: RouletteSwipeListener {
            override fun onSwipeToLeft(diff: Float) {
                Log.i("eunjin", "onSwipeToLeft $diff")
                startRouletteRotation(abs(diff))
            }

            override fun onSwipeToRight(diff: Float) {
                 Log.i("eunjin", "onSwipeToRight $diff")
                startRouletteRotation(abs(diff))
            }
        }))
    }
}
