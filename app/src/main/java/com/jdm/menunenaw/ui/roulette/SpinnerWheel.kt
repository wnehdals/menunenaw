package com.jdm.menunenaw.ui.roulette

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.jdm.menunenaw.R
import kotlin.math.cos
import kotlin.math.sin

class SpinnerWheel (
    context: Context,
    private val dataList: List<String>
) : View(context) {

    private val DEFAULT_PADDING = 20
    private val CENTER_DOT_SIZE = 20

    private val rouletteSize
        get() = dataList.size

    private var centerX = 0f
    private var centerY = 0f

    private val strokePaint = Paint() // 테두리 페인트
    private val fillPaint = Paint() // 내부 원 페인트
    private val fillColorIds = listOf(
        R.color.spinner_wheel_fill_1,
        R.color.spinner_wheel_fill_2,
        R.color.spinner_wheel_fill_3,
        R.color.spinner_wheel_fill_4,
        R.color.spinner_wheel_fill_5,
        R.color.spinner_wheel_fill_6
    )
    private val strokeArcPaint = Paint() // 내부 구분선 페인트
    private val textPaint = Paint() // 텍스트 페인트

    init {
        strokePaint.apply {
            color = ContextCompat.getColor(context, R.color.spinner_wheel_stroke)
            style = Paint.Style.STROKE
            strokeWidth = 20f
            isAntiAlias = true
        }
        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        strokeArcPaint.apply {
            color = ContextCompat.getColor(context, R.color.spinner_wheel_stroke)
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        }
        textPaint.apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
        }
    }

    /** 룰렛 돌리기*/
    fun rotateRoulette(toDegrees: Float, duration: Long) {
        val rotateAnim = RotateAnimation( 0f, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true
        startAnimation(rotateAnim)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val rectLeft = left + paddingLeft + DEFAULT_PADDING
        val rectRight = right - paddingRight - DEFAULT_PADDING
        val rectTop = height / 2f - rectRight / 2f + paddingTop + DEFAULT_PADDING
        val rectBottom = height / 2f + rectRight / 2f - paddingRight - DEFAULT_PADDING

        val rectF = RectF(rectLeft.toFloat(), rectTop, rectRight.toFloat(), rectBottom)

        centerX = (rectF.left + rectF.right) / 2f
        centerY = (rectF.top + rectF.bottom) / 2f

        drawRoulette(canvas, rectF)
        drawCenterDot(canvas, RectF(centerX-CENTER_DOT_SIZE, centerY+CENTER_DOT_SIZE, centerX+CENTER_DOT_SIZE, centerY-CENTER_DOT_SIZE))
    }

    /**룰렛 그리는 함수 */
    private fun drawRoulette(canvas: Canvas, rectF: RectF) {
        // 테두리 그리기
        canvas.drawArc(rectF, 0f, 360f, false, strokePaint)

        if (rouletteSize in 2..10) {
            val sweepAngle = 360f / rouletteSize.toFloat()
            val radius = (rectF.right - rectF.left) / 2 * 0.68

            // 내부 부채꼴 그리기
            for (i in 0 until rouletteSize) {
                fillPaint.color = ContextCompat.getColor(context, fillColorIds[i % fillColorIds.size])
                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
                val x = (centerX + (radius * cos(medianAngle))).toFloat()
                val y = (centerY + (radius * sin(medianAngle))).toFloat() + DEFAULT_PADDING
                var text = dataList[i]
                textPaint.textSize = when (text.length) {
                    in 0..3 -> 50f
                    in 4..5 -> 45f
                    else -> {
                        text = text.substring(0, 5) + ".."
                        35f
                    }
                }
                canvas.drawText(text, x, y, textPaint)
            }

            // 내부 구분선 그리기
            val r = rectF.width() / 2
            for (i in 0 until rouletteSize) {
               val startAngle = if (i == 0) 0f else sweepAngle * i * (Math.PI / 180)
                 canvas.drawLine(rectF.centerX(),
                    rectF.centerY(),
                    centerX + (r * cos(startAngle.toDouble()).toFloat()),
                    centerY + r * sin(startAngle.toDouble()).toFloat(),
                    strokeArcPaint)
            }
        } else {
            throw IndexOutOfBoundsException("size out of roulette")
        }
    }

    /**가운데 중심 작은 원 그리는 함수 */
    private fun drawCenterDot(canvas: Canvas, rectF: RectF) {
        fillPaint.color = ContextCompat.getColor(context, R.color.spinner_wheel_stroke)
        canvas.drawArc(rectF, 0f, 360f, false, strokePaint)
    }

}
