package com.ext.animation_background.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.BlurMaskFilter
import android.util.AttributeSet
import com.ext.animation_background.R
import com.ext.animation_background.base.BaseAnimationView
import com.ext.animation_background.utils.AnimationConstants
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Shape Morphing Animation Background View
 * Displays abstract shapes that morph, scale, rotate, and transform smoothly
 *
 * Perfect for: Modern UI, creative apps, portfolio apps
 */
class ShapeMorphingAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseAnimationView(context, attrs, defStyleAttr) {

    private val shapePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Animation properties
    private var primaryColor = AnimationConstants.DEFAULT_SHAPE_PRIMARY_COLOR
    private var secondaryColor = AnimationConstants.DEFAULT_SHAPE_SECONDARY_COLOR
    private var shapeCount = AnimationConstants.DEFAULT_SHAPE_COUNT
    private var minSize = 0f
    private var maxSize = 0f
    private var morphDuration = AnimationConstants.DEFAULT_SHAPE_MORPH_DURATION
    private var enableRotation = AnimationConstants.DEFAULT_SHAPE_ENABLE_ROTATION
    private var rotationSpeed = AnimationConstants.DEFAULT_SHAPE_ROTATION_SPEED
    private var shapeAlpha = AnimationConstants.DEFAULT_SHAPE_ALPHA
    private var blurRadius = 0f
    private var autoStart = AnimationConstants.DEFAULT_SHAPE_AUTO_START

    // Shape data
    private val shapes = mutableListOf<MorphingShape>()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null) // Required for blur effect
        initAttributes(attrs)
        setupPaints()
    }

    override fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ShapeMorphingAnimationView)

            primaryColor = typedArray.getColor(
                R.styleable.ShapeMorphingAnimationView_ab_shapePrimaryColor,
                AnimationConstants.DEFAULT_SHAPE_PRIMARY_COLOR
            )

            secondaryColor = typedArray.getColor(
                R.styleable.ShapeMorphingAnimationView_ab_shapeSecondaryColor,
                AnimationConstants.DEFAULT_SHAPE_SECONDARY_COLOR
            )

            shapeCount = typedArray.getInt(
                R.styleable.ShapeMorphingAnimationView_ab_shapeCount,
                AnimationConstants.DEFAULT_SHAPE_COUNT
            )

            minSize = typedArray.getDimension(
                R.styleable.ShapeMorphingAnimationView_ab_shapeMinSize,
                dpToPx(AnimationConstants.DEFAULT_SHAPE_MIN_SIZE)
            )

            maxSize = typedArray.getDimension(
                R.styleable.ShapeMorphingAnimationView_ab_shapeMaxSize,
                dpToPx(AnimationConstants.DEFAULT_SHAPE_MAX_SIZE)
            )

            morphDuration = typedArray.getInt(
                R.styleable.ShapeMorphingAnimationView_ab_shapeMorphDuration,
                AnimationConstants.DEFAULT_SHAPE_MORPH_DURATION
            )

            enableRotation = typedArray.getBoolean(
                R.styleable.ShapeMorphingAnimationView_ab_shapeEnableRotation,
                AnimationConstants.DEFAULT_SHAPE_ENABLE_ROTATION
            )

            rotationSpeed = typedArray.getFloat(
                R.styleable.ShapeMorphingAnimationView_ab_shapeRotationSpeed,
                AnimationConstants.DEFAULT_SHAPE_ROTATION_SPEED
            )

            shapeAlpha = typedArray.getFloat(
                R.styleable.ShapeMorphingAnimationView_ab_shapeAlpha,
                AnimationConstants.DEFAULT_SHAPE_ALPHA
            )

            blurRadius = typedArray.getDimension(
                R.styleable.ShapeMorphingAnimationView_ab_shapeBlurRadius,
                dpToPx(AnimationConstants.DEFAULT_SHAPE_BLUR_RADIUS)
            )

            autoStart = typedArray.getBoolean(
                R.styleable.ShapeMorphingAnimationView_ab_shapeAutoStart,
                AnimationConstants.DEFAULT_SHAPE_AUTO_START
            )

            typedArray.recycle()
        }
    }

    private fun setupPaints() {
        shapePaint.style = Paint.Style.FILL
        shapePaint.alpha = (shapeAlpha * 255).toInt()

        if (blurRadius > 0) {
            shapePaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeShapes()
    }

    private fun initializeShapes() {
        shapes.clear()
        val w = width.toFloat()
        val h = height.toFloat()

        if (w == 0f || h == 0f) return

        repeat(shapeCount) { index ->
            val colorProgress = index.toFloat() / shapeCount.toFloat()
            val color = blendColors(primaryColor, secondaryColor, colorProgress)

            shapes.add(
                MorphingShape(
                    x = Random.nextFloat() * w,
                    y = Random.nextFloat() * h,
                    currentSize = Random.nextFloat() * (maxSize - minSize) + minSize,
                    targetSize = Random.nextFloat() * (maxSize - minSize) + minSize,
                    rotation = Random.nextFloat() * 360f,
                    rotationSpeed = (Random.nextFloat() - 0.5f) * 2f * rotationSpeed,
                    color = color,
                    morphProgress = 0f,
                    morphDuration = morphDuration.toFloat()
                )
            )
        }
    }

    override fun updateAnimation(deltaTime: Long) {
        shapes.forEach { shape ->
            // Update morph progress
            shape.morphProgress += deltaTime / shape.morphDuration

            if (shape.morphProgress >= 1f) {
                shape.morphProgress = 0f
                shape.currentSize = shape.targetSize
                shape.targetSize = Random.nextFloat() * (maxSize - minSize) + minSize
            }

            // Update rotation
            if (enableRotation) {
                shape.rotation += shape.rotationSpeed * (deltaTime / 16f)
                if (shape.rotation > 360f) shape.rotation -= 360f
                if (shape.rotation < 0f) shape.rotation += 360f
            }
        }
    }

    override fun drawAnimation(canvas: Canvas) {
        shapes.forEach { shape ->
            val easedProgress = easeInOutCubic(shape.morphProgress)
            val interpolatedSize = shape.currentSize +
                    (shape.targetSize - shape.currentSize) * easedProgress

            canvas.save()
            canvas.translate(shape.x, shape.y)
            canvas.rotate(shape.rotation)

            shapePaint.color = shape.color

            // Draw rounded rectangle (morphing shape)
            val halfSize = interpolatedSize / 2
            val cornerRadius = interpolatedSize * 0.3f
            canvas.drawRoundRect(
                -halfSize, -halfSize,
                halfSize, halfSize,
                cornerRadius, cornerRadius,
                shapePaint
            )

            canvas.restore()
        }
    }

    override fun shouldAutoStart(): Boolean = autoStart

    /**
     * Smooth cubic easing function
     */
    private fun easeInOutCubic(t: Float): Float {
        return if (t < 0.5f) {
            4f * t * t * t
        } else {
            1f - (-2f * t + 2f) * (-2f * t + 2f) * (-2f * t + 2f) / 2f
        }
    }

    /**
     * Blend two colors based on progress (0-1)
     */
    private fun blendColors(color1: Int, color2: Int, progress: Float): Int {
        val inverseProgress = 1 - progress

        val r = (android.graphics.Color.red(color1) * inverseProgress +
                android.graphics.Color.red(color2) * progress).toInt()
        val g = (android.graphics.Color.green(color1) * inverseProgress +
                android.graphics.Color.green(color2) * progress).toInt()
        val b = (android.graphics.Color.blue(color1) * inverseProgress +
                android.graphics.Color.blue(color2) * progress).toInt()
        val a = (android.graphics.Color.alpha(color1) * inverseProgress +
                android.graphics.Color.alpha(color2) * progress).toInt()

        return android.graphics.Color.argb(a, r, g, b)
    }

    /**
     * Update shape colors dynamically
     */
    fun setShapeColors(primary: Int, secondary: Int) {
        primaryColor = primary
        secondaryColor = secondary
        initializeShapes()
        invalidate()
    }

    /**
     * Data class representing a morphing shape
     */
    private data class MorphingShape(
        val x: Float,
        val y: Float,
        var currentSize: Float,
        var targetSize: Float,
        var rotation: Float,
        val rotationSpeed: Float,
        val color: Int,
        var morphProgress: Float,
        val morphDuration: Float
    )
}