package com.jdm.menunenaw.ui.roulette.custom

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

interface RouletteSwipeListener {
    fun onSwipeToRight(diff: Float)
}

class RouletteTouchListener(context: Context, rouletteSwipeListener: RouletteSwipeListener): View.OnTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(context, GestureListener(rouletteSwipeListener))

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener(val rouletteSwipeListener: RouletteSwipeListener) : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 0
        private val SWIPE_VELOCITY_THRESHOLD = 0

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                val diffY = e2!!.y - e1!!.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX * velocityX > 0) {
                        val result = if (diffX > 0) {
                            diffX + (diffX * velocityX / 4000)
                        } else {
                            -1 * diffX + (diffX * velocityX / 4000)
                        }
                        rouletteSwipeListener.onSwipeToRight(result)
                        return true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }
}