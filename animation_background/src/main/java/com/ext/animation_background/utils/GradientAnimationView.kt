package com.ext.animation_background.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import com.ext.animation_background.R
import com.ext.animation_background.base.BaseAnimationView
import com.ext.animation_background.utils.AnimationConstants
import kotlin.math.abs
import kotlin.math.sin

/**
 * Gradient Animation Background View
 * Displays smooth animated gradient transitions
 *
 * Perfect for: Login screens, splash screens, professional UI
 */
class GradientAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseAnimationView(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Animation properties
    private var startColor = AnimationConstants.DEFAULT_GRADIENT_START_COLOR
    private var endColor = AnimationConstants.DEFAULT_GRADIENT_END_COLOR
    private var middleColor: Int? = null
    private var duration = AnimationConstants.DEFAULT_GRADIENT_DURATION
    private var direction = AnimationConstants.GradientDirection.HORIZONTAL
    private var autoStart = AnimationConstants.DEFAULT_GRADIENT_AUTO_START
    private var enableReverse = AnimationConstants.DEFAULT_GRADIENT_REVERSE

    // Animation state
    private var animationProgress = 0f
    private var isReversing = false

    init {
        initAttributes(attrs)
    }

    override fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.GradientAnimationView)

            startColor = typedArray.getColor(
                R.styleable.GradientAnimationView_ab_gradientStartColor,
                AnimationConstants.DEFAULT_GRADIENT_START_COLOR
            )

            endColor = typedArray.getColor(
                R.styleable.GradientAnimationView_ab_gradientEndColor,
                AnimationConstants.DEFAULT_GRADIENT_END_COLOR
            )

            if (typedArray.hasValue(R.styleable.GradientAnimationView_ab_gradientMiddleColor)) {
                middleColor = typedArray.getColor(
                    R.styleable.GradientAnimationView_ab_gradientMiddleColor,
                    0
                )
            }

            duration = typedArray.getInt(
                R.styleable.GradientAnimationView_ab_gradientDuration,
                AnimationConstants.DEFAULT_GRADIENT_DURATION
            )

            val directionValue = typedArray.getInt(
                R.styleable.GradientAnimationView_ab_gradientDirection,
                0
            )
            direction = AnimationConstants.GradientDirection.values()[directionValue]

            autoStart = typedArray.getBoolean(
                R.styleable.GradientAnimationView_ab_gradientAutoStart,
                AnimationConstants.DEFAULT_GRADIENT_AUTO_START
            )

            enableReverse = typedArray.getBoolean(
                R.styleable.GradientAnimationView_ab_gradientReverse,
                AnimationConstants.DEFAULT_GRADIENT_REVERSE
            )

            typedArray.recycle()
        }
    }

    override fun updateAnimation(deltaTime: Long) {
        val increment = (deltaTime.toFloat() / duration) * 2f

        if (!isReversing) {
            animationProgress += increment
            if (animationProgress >= 1f) {
                animationProgress = 1f
                if (enableReverse) {
                    isReversing = true
                } else {
                    animationProgress = 0f
                }
            }
        } else {
            animationProgress -= increment
            if (animationProgress <= 0f) {
                animationProgress = 0f
                isReversing = false
            }
        }
    }

    override fun drawAnimation(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()

        if (w == 0f || h == 0f) return

        // Calculate gradient coordinates based on direction
        val (x0, y0, x1, y1) = when (direction) {
            AnimationConstants.GradientDirection.HORIZONTAL -> {
                floatArrayOf(0f, h / 2, w, h / 2)
            }
            AnimationConstants.GradientDirection.VERTICAL -> {
                floatArrayOf(w / 2, 0f, w / 2, h)
            }
            AnimationConstants.GradientDirection.DIAGONAL_TL_BR -> {
                floatArrayOf(0f, 0f, w, h)
            }
            AnimationConstants.GradientDirection.DIAGONAL_TR_BL -> {
                floatArrayOf(w, 0f, 0f, h)
            }
        }

        // Apply smooth easing to progress
        val easedProgress = easeInOutSine(animationProgress)

        // Create gradient shader
        val shader = if (middleColor != null) {
            LinearGradient(
                x0, y0, x1, y1,
                intArrayOf(startColor, middleColor!!, endColor),
                floatArrayOf(0f, easedProgress, 1f),
                Shader.TileMode.CLAMP
            )
        } else {
            LinearGradient(
                x0, y0, x1, y1,
                intArrayOf(startColor, endColor),
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP
            )
        }

        paint.shader = shader
        canvas.drawRect(0f, 0f, w, h, paint)
    }

    override fun shouldAutoStart(): Boolean = autoStart

    /**
     * Smooth easing function for natural animation
     */
    private fun easeInOutSine(t: Float): Float {
        return (1f - kotlin.math.cos(t * Math.PI.toFloat())) / 2f
    }

    /**
     * Update gradient colors programmatically
     */
    fun setGradientColors(start: Int, end: Int, middle: Int? = null) {
        startColor = start
        endColor = end
        middleColor = middle
        invalidate()
    }

    /**
     * Update animation duration
     */
    fun setAnimationDuration(durationMs: Int) {
        duration = durationMs
    }
}