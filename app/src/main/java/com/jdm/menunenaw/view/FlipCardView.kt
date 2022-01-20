package com.jdm.menunenaw.view

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.jdm.menunenaw.R

class FlipCardView @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
    private val defStyleRes: Int = 0
): ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private val view: View = LayoutInflater.from(context).inflate(R.layout.flip_card_container, this, true)

    var isChoice = false
    var frontText: TextView = view.findViewById<TextView>(R.id.flip_card_view_front_text)
    var front = view.findViewById<ConstraintLayout>(R.id.flip_card_front_constraintlayout)
    var back = view.findViewById<ConstraintLayout>(R.id.flip_card_back_constraintlayout)


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
            recycle()
        }
    }
    fun flip() {
        if (isChoice == false) {
            frontAnimation.setTarget(back)
            backAnimation.setTarget(front)
            backAnimation.start()
            frontAnimation.start()
            isChoice = true
        } else {
            frontAnimation.setTarget(front)
            backAnimation.setTarget(back)
            frontAnimation.start()
            backAnimation.start()
            isChoice = false
        }
    }

}
