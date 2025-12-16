package com.ext.animation_background.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import com.ext.animation_background.R
import com.ext.animation_background.base.BaseAnimationView
import com.ext.animation_background.utils.AnimationConstants
import kotlin.math.PI
import kotlin.math.sin

/**
 * Wave Animation Background View
 * Displays smooth moving wave shapes with continuous looping motion
 *
 * Perfect for: Meditation apps, fitness apps, weather UI
 */
class WaveAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseAnimationView(context, attrs, defStyleAttr) {

    private val wavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val wavePath = Path()

    // Animation properties
    private var primaryColor = AnimationConstants.DEFAULT_WAVE_PRIMARY_COLOR
    private var secondaryColor = AnimationConstants.DEFAULT_WAVE_SECONDARY_COLOR
    private var waveCount = AnimationConstants.DEFAULT_WAVE_COUNT
    private var amplitude = 0f
    private var frequency = AnimationConstants.DEFAULT_WAVE_FREQUENCY
    private var speedMultiplier = AnimationConstants.DEFAULT_WAVE_SPEED
    private var waveAlpha = AnimationConstants.DEFAULT_WAVE_ALPHA
    private var direction = AnimationConstants.WaveDirection.LEFT_TO_RIGHT
    private var autoStart = AnimationConstants.DEFAULT_WAVE_AUTO_START

    // Animation state
    private var phaseShift = 0f
    private val waves = mutableListOf<Wave>()

    init {
        initAttributes(attrs)
        setupPaints()
    }

    override fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.WaveAnimationView)

            primaryColor = typedArray.getColor(
                R.styleable.WaveAnimationView_ab_wavePrimaryColor,
                AnimationConstants.DEFAULT_WAVE_PRIMARY_COLOR
            )

            secondaryColor = typedArray.getColor(
                R.styleable.WaveAnimationView_ab_waveSecondaryColor,
                AnimationConstants.DEFAULT_WAVE_SECONDARY_COLOR
            )

            waveCount = typedArray.getInt(
                R.styleable.WaveAnimationView_ab_waveCount,
                AnimationConstants.DEFAULT_WAVE_COUNT
            )

            amplitude = typedArray.getDimension(
                R.styleable.WaveAnimationView_ab_waveAmplitude,
                dpToPx(AnimationConstants.DEFAULT_WAVE_AMPLITUDE)
            )

            frequency = typedArray.getFloat(
                R.styleable.WaveAnimationView_ab_waveFrequency,
                AnimationConstants.DEFAULT_WAVE_FREQUENCY
            )

            speedMultiplier = typedArray.getFloat(
                R.styleable.WaveAnimationView_ab_waveSpeed,
                AnimationConstants.DEFAULT_WAVE_SPEED
            )

            waveAlpha = typedArray.getFloat(
                R.styleable.WaveAnimationView_ab_waveAlpha,
                AnimationConstants.DEFAULT_WAVE_ALPHA
            )

            val directionValue = typedArray.getInt(
                R.styleable.WaveAnimationView_ab_waveDirection,
                0
            )
            direction = AnimationConstants.WaveDirection.values()[directionValue]

            autoStart = typedArray.getBoolean(
                R.styleable.WaveAnimationView_ab_waveAutoStart,
                AnimationConstants.DEFAULT_WAVE_AUTO_START
            )

            typedArray.recycle()
        }

        initializeWaves()
    }

    private fun setupPaints() {
        wavePaint.style = Paint.Style.FILL
        wavePaint.alpha = (waveAlpha * 255).toInt()
    }

    private fun initializeWaves() {
        waves.clear()
        val colorStep = if (waveCount > 1) {
            1f / (waveCount - 1)
        } else {
            0f
        }

        repeat(waveCount) { index ->
            val colorProgress = index * colorStep
            val color = blendColors(primaryColor, secondaryColor, colorProgress)
            val verticalOffset = index * (amplitude * 0.3f)
            val speedOffset = index * 0.2f

            waves.add(
                Wave(
                    color = color,
                    verticalOffset = verticalOffset,
                    speedOffset = speedOffset
                )
            )
        }
    }

    override fun updateAnimation(deltaTime: Long) {
        val speed = 0.002f * speedMultiplier * (deltaTime / 16f)
        val increment = if (direction == AnimationConstants.WaveDirection.LEFT_TO_RIGHT) {
            speed
        } else {
            -speed
        }

        phaseShift += increment
        if (phaseShift > 2 * PI) {
            phaseShift -= (2 * PI).toFloat()
        } else if (phaseShift < 0) {
            phaseShift += (2 * PI).toFloat()
        }
    }

    override fun drawAnimation(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()

        if (w == 0f || h == 0f) return

        waves.forEach { wave ->
            drawWave(canvas, w, h, wave)
        }
    }

    private fun drawWave(canvas: Canvas, width: Float, height: Float, wave: Wave) {
        wavePath.reset()

        val baseY = height * 0.6f + wave.verticalOffset
        val points = 200
        val step = width / points

        wavePath.moveTo(0f, height)

        for (i in 0..points) {
            val x = i * step
            val angle = (x / width) * frequency * 2 * PI + phaseShift + wave.speedOffset
            val y = baseY + sin(angle.toDouble()).toFloat() * amplitude

            if (i == 0) {
                wavePath.lineTo(x, y)
            } else {
                wavePath.lineTo(x, y)
            }
        }

        wavePath.lineTo(width, height)
        wavePath.lineTo(0f, height)
        wavePath.close()

        wavePaint.color = wave.color
        canvas.drawPath(wavePath, wavePaint)
    }

    override fun shouldAutoStart(): Boolean = autoStart

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
     * Update wave colors dynamically
     */
    fun setWaveColors(primary: Int, secondary: Int) {
        primaryColor = primary
        secondaryColor = secondary
        initializeWaves()
        invalidate()
    }

    /**
     * Data class representing a single wave
     */
    private data class Wave(
        val color: Int,
        val verticalOffset: Float,
        val speedOffset: Float
    )
}