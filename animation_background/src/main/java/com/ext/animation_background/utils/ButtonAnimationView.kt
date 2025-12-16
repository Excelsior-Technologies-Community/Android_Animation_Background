package com.ext.animation_background.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import com.ext.animation_background.R
import com.ext.animation_background.base.BaseAnimationView
import com.ext.animation_background.utils.AnimationConstants

/**
 * Button Animation Background View
 * Displays an animated dashed border that moves around the view perimeter
 *
 * Perfect for: Call-to-action buttons, loading indicators, interactive UI
 */
class ButtonAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseAnimationView(context, attrs, defStyleAttr) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    // Animation properties
    private var borderColor = AnimationConstants.DEFAULT_BUTTON_BORDER_COLOR
    private var borderWidth = 0f
    private var dashLength = 0f
    private var dashGap = 0f
    private var animationSpeed = AnimationConstants.DEFAULT_BUTTON_ANIMATION_SPEED
    private var cornerRadius = 0f
    private var direction = AnimationConstants.ButtonDirection.CLOCKWISE
    private var autoStart = AnimationConstants.DEFAULT_BUTTON_AUTO_START

    // Animation state
    private var phase = 0f

    init {
        initAttributes(attrs)
        setupPaints()
    }

    override fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ButtonAnimationView)

            borderColor = typedArray.getColor(
                R.styleable.ButtonAnimationView_ab_buttonBorderColor,
                AnimationConstants.DEFAULT_BUTTON_BORDER_COLOR
            )

            borderWidth = typedArray.getDimension(
                R.styleable.ButtonAnimationView_ab_buttonBorderWidth,
                dpToPx(AnimationConstants.DEFAULT_BUTTON_BORDER_WIDTH)
            )

            dashLength = typedArray.getDimension(
                R.styleable.ButtonAnimationView_ab_buttonDashLength,
                dpToPx(AnimationConstants.DEFAULT_BUTTON_DASH_LENGTH)
            )

            dashGap = typedArray.getDimension(
                R.styleable.ButtonAnimationView_ab_buttonDashGap,
                dpToPx(AnimationConstants.DEFAULT_BUTTON_DASH_GAP)
            )

            animationSpeed = typedArray.getFloat(
                R.styleable.ButtonAnimationView_ab_buttonAnimationSpeed,
                AnimationConstants.DEFAULT_BUTTON_ANIMATION_SPEED
            )

            cornerRadius = typedArray.getDimension(
                R.styleable.ButtonAnimationView_ab_buttonCornerRadius,
                dpToPx(AnimationConstants.DEFAULT_BUTTON_CORNER_RADIUS)
            )

            val directionValue = typedArray.getInt(
                R.styleable.ButtonAnimationView_ab_buttonDirection,
                0
            )
            direction = AnimationConstants.ButtonDirection.values()[directionValue]

            autoStart = typedArray.getBoolean(
                R.styleable.ButtonAnimationView_ab_buttonAutoStart,
                AnimationConstants.DEFAULT_BUTTON_AUTO_START
            )

            typedArray.recycle()
        }
    }

    private fun setupPaints() {
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
    }

    override fun updateAnimation(deltaTime: Long) {
        val increment = (deltaTime / 16f) * animationSpeed
        phase += if (direction == AnimationConstants.ButtonDirection.CLOCKWISE) {
            increment
        } else {
            -increment
        }

        val dashPeriod = dashLength + dashGap
        if (phase > dashPeriod) {
            phase -= dashPeriod
        } else if (phase < -dashPeriod) {
            phase += dashPeriod
        }
    }

    override fun drawAnimation(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()

        if (w == 0f || h == 0f) return

        borderPaint.pathEffect = DashPathEffect(floatArrayOf(dashLength, dashGap), phase)

        val inset = borderWidth / 2f
        canvas.drawRoundRect(
            inset, inset,
            w - inset, h - inset,
            cornerRadius, cornerRadius,
            borderPaint
        )
    }

    override fun shouldAutoStart(): Boolean = autoStart

    /**
     * Update border color programmatically
     */
    fun setBorderColor(color: Int) {
        borderColor = color
        borderPaint.color = color
        invalidate()
    }

    /**
     * Update animation speed programmatically
     */
    fun setAnimationSpeed(speed: Float) {
        animationSpeed = speed
    }
}