package com.ext.animation_background.base

import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.ext.animation_background.utils.AnimationConstants

/**
 * Base class for all animation views
 * Handles lifecycle management and animation timing
 */
abstract class BaseAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    protected var isAnimating = false
    private val animationHandler = Handler(Looper.getMainLooper())
    private var lastFrameTime = 0L

    /**
     * Initialize animation parameters from XML attributes
     */
    protected abstract fun initAttributes(attrs: AttributeSet?)

    /**
     * Update animation state based on elapsed time
     * @param deltaTime Time elapsed since last frame in milliseconds
     */
    protected abstract fun updateAnimation(deltaTime: Long)

    /**
     * Draw the animation frame
     */
    protected abstract fun drawAnimation(canvas: Canvas)

    /**
     * Animation loop runnable
     */
    private val animationRunnable = object : Runnable {
        override fun run() {
            if (!isAnimating) return

            val currentTime = System.currentTimeMillis()
            val deltaTime = if (lastFrameTime == 0L) {
                AnimationConstants.FRAME_DELAY
            } else {
                currentTime - lastFrameTime
            }
            lastFrameTime = currentTime

            updateAnimation(deltaTime)
            invalidate()

            animationHandler.postDelayed(this, AnimationConstants.FRAME_DELAY)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawAnimation(canvas)
    }

    /**
     * Start the animation
     */
    fun startAnimation() {
        if (isAnimating) return
        isAnimating = true
        lastFrameTime = 0L
        animationHandler.post(animationRunnable)
    }

    /**
     * Stop the animation
     */
    fun stopAnimation() {
        isAnimating = false
        animationHandler.removeCallbacks(animationRunnable)
    }

    /**
     * Pause the animation (can be resumed)
     */
    fun pauseAnimation() {
        if (!isAnimating) return
        isAnimating = false
        animationHandler.removeCallbacks(animationRunnable)
    }

    /**
     * Resume the animation
     */
    fun resumeAnimation() {
        if (isAnimating) return
        startAnimation()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (shouldAutoStart()) {
            startAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE && shouldAutoStart()) {
            startAnimation()
        } else if (visibility != VISIBLE) {
            pauseAnimation()
        }
    }

    /**
     * Override this to control auto-start behavior
     */
    protected open fun shouldAutoStart(): Boolean = true

    /**
     * Convert dp to pixels
     */
    protected fun dpToPx(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}