package com.jdm.menunenaw.ui.roulette.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import com.jdm.menunenaw.R
import kotlin.math.cos
import kotlin.math.sin
import android.animation.ObjectAnimator
import android.animation.Animator
import androidx.lifecycle.MutableLiveData


class SpinnerWheel (
    context: Context,
    private val dataList: List<String>
) : View(context) {

    private val DEFAULT_PADDING = 20
    private val CENTER_DOT_SIZE = 150

    private val rouletteSize
        get() = dataList.size

    private val sweepAngle
        get() = 360f / rouletteSize.toFloat()

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

    private var mFromDegrees = 0f
    private var mToDegrees = 0f

    private var mMutableLiveData: MutableLiveData<String>? = null
    private var mEndAction: ((Int) -> Unit)? = null

    /** 결과 리스너 등록*/
    fun setOnRouletteResultListener(mutableLiveData: MutableLiveData<String>, endAction: (Int) -> Unit) {
        mMutableLiveData = mutableLiveData
        mEndAction = endAction
    }

    /** 룰렛 돌리기*/
    fun rotateRoulette(toDegrees: Float, duration: Long) {
        mToDegrees += toDegrees
        val rotateAnim: ObjectAnimator = ObjectAnimator.ofFloat(
            this,
            View.ROTATION,
            mFromDegrees,
            mToDegrees
        )
        rotateAnim.duration = duration

        mFromDegrees = mToDegrees
        rotateAnim.addUpdateListener {
            // 현재 가리키고 있는 대상 계산
            (it.animatedValue as? Float)?.let { value ->
                mMutableLiveData?.postValue(calCurrentItem(value).second)
            }
        }
        val animListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) { // 종료됐을 때 호출
                mEndAction?.invoke(calCurrentItem(mFromDegrees).first)
            }
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
        }
        rotateAnim.addListener(animListener)
        rotateAnim.start()
    }

    private fun calCurrentItem(currentDegree: Float) : Pair<Int, String> {
        val simpleDegree = (currentDegree  + 90) % 360 // 270도 지점부터 그려짐
        val index = rouletteSize-1 - (((simpleDegree / sweepAngle).toInt()) % rouletteSize)
        return Pair(index, dataList[index])
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
            val textRectF = RectF(rectF.left + 140, rectF.top + 140, rectF.right - 140, rectF.bottom - 140)
            // 내부 부채꼴 그리기
            for (i in 0 until rouletteSize) {
                fillPaint.color = ContextCompat.getColor(context, fillColorIds[i % fillColorIds.size])
                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                var text = dataList[i]
                textPaint.textSize = when (text.length) {
                    in 0..3 -> 60f
                    in 4..5 -> 55f
                    else -> {
                        text = text.substring(0, 5) + ".."
                        45f
                    }
                }

                // 텍스트 넣기
                val path = Path()
                path.addArc(textRectF, startAngle, sweepAngle)
                canvas.drawTextOnPath(text, path, 0f, 0f, textPaint)
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
        canvas.drawArc(rectF, 0f, 360f, false, fillPaint)
    }

}
