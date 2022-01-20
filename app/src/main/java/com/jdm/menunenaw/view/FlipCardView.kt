package com.jdm.menunenaw.view

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.jdm.menunenaw.R

class FlipCardView @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private val view: View =
        LayoutInflater.from(context).inflate(R.layout.flip_card_container, this, true)

    var isChoice = false
    var frontText: TextView = view.findViewById<TextView>(R.id.flip_card_view_front_text)
    var front = view.findViewById<ConstraintLayout>(R.id.flip_card_front_constraintlayout)
    var back = view.findViewById<ConstraintLayout>(R.id.flip_card_back_constraintlayout)
    var innerMargin = 36
    var textColorId = R.color.c_ffffffff

    @SuppressLint("ResourceType")
    var frontAnimation = AnimatorInflater.loadAnimator(context, R.anim.flip_card_front_animator)

    @SuppressLint("ResourceType")
    var backAnimation = AnimatorInflater.loadAnimator(context, R.anim.flip_card_back_animator)

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.flip_card_view,
            defStyleAttr,
            defStyleRes
        ).apply {
            isChoice = getBoolean(R.styleable.flip_card_view_isChoice, false)
            frontText.setText(getString(R.styleable.flip_card_view_front_text))
            frontText.setTextColor(context.resources.getColor(textColorId))
            recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setInnerMargin(front)
        setInnerMargin(back)


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var temp = ((this@FlipCardView.layoutParams.width) * 0.2f)
        innerMargin = (temp).toInt()

    }
    fun setInnerMargin(layout: ConstraintLayout) {
        var lp = layout.layoutParams as ConstraintLayout.LayoutParams
        lp.topMargin = innerMargin
        lp.marginStart = innerMargin
        lp.marginEnd = innerMargin
        lp.bottomMargin = innerMargin
        layout.layoutParams = lp

    }
    fun setFrontTextColor(id: Int) {
        textColorId = id
        frontText.setTextColor(context.resources.getColor(textColorId))
        invalidate()
    }
    fun flip() {
        if (isChoice == false) {
            frontAnimation.setTarget(back)
            backAnimation.setTarget(front)
            backAnimation.start()
            frontAnimation.start()
            isChoice = true
        }
    }

}
