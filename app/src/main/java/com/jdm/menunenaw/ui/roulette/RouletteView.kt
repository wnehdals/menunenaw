package com.jdm.menunenaw.ui.roulette

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jdm.menunenaw.R
import com.jdm.menunenaw.databinding.LayoutRouletteViewBinding

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

    init {
        binding.constraintLayout.addView(
            SpinnerWheel(
                context = context,
                dataList = listOf("떡볶이", "탕수육", "순대", "튀김", "라면", "돼지국밥", "육회", "케이크", "세상에서제일맛있는집")
            )
        )
    }

}
