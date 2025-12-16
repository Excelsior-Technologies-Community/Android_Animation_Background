package com.ext.animation_background.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import com.ext.animation_background.R
import com.ext.animation_background.base.BaseAnimationView
import com.ext.animation_background.utils.AnimationConstants
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * Particle Animation Background View
 * Displays floating animated particles with optional connection lines
 *
 * Perfect for: Music apps, gaming dashboards, tech-themed UI
 */
class ParticleAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseAnimationView(context, attrs, defStyleAttr) {

    private val particlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Animation properties
    private var particleCount = AnimationConstants.DEFAULT_PARTICLE_COUNT
    private var minSize = 0f
    private var maxSize = 0f
    private var particleColor = AnimationConstants.DEFAULT_PARTICLE_COLOR
    private var speedMultiplier = AnimationConstants.DEFAULT_PARTICLE_SPEED
    private var direction = AnimationConstants.ParticleDirection.RANDOM
    private var connectLines = AnimationConstants.DEFAULT_PARTICLE_CONNECT_LINES
    private var connectionDistance = 0f
    private var particleAlpha = AnimationConstants.DEFAULT_PARTICLE_ALPHA
    private var autoStart = AnimationConstants.DEFAULT_PARTICLE_AUTO_START

    // Particle data
    private val particles = mutableListOf<Particle>()

    init {
        initAttributes(attrs)
        setupPaints()
    }

    override fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ParticleAnimationView)

            particleCount = typedArray.getInt(
                R.styleable.ParticleAnimationView_ab_particleCount,
                AnimationConstants.DEFAULT_PARTICLE_COUNT
            )

            minSize = typedArray.getDimension(
                R.styleable.ParticleAnimationView_ab_particleMinSize,
                dpToPx(AnimationConstants.DEFAULT_PARTICLE_MIN_SIZE)
            )

            maxSize = typedArray.getDimension(
                R.styleable.ParticleAnimationView_ab_particleMaxSize,
                dpToPx(AnimationConstants.DEFAULT_PARTICLE_MAX_SIZE)
            )

            particleColor = typedArray.getColor(
                R.styleable.ParticleAnimationView_ab_particleColor,
                AnimationConstants.DEFAULT_PARTICLE_COLOR
            )

            speedMultiplier = typedArray.getFloat(
                R.styleable.ParticleAnimationView_ab_particleSpeed,
                AnimationConstants.DEFAULT_PARTICLE_SPEED
            )

            val directionValue = typedArray.getInt(
                R.styleable.ParticleAnimationView_ab_particleDirection,
                0
            )
            direction = AnimationConstants.ParticleDirection.values()[directionValue]

            connectLines = typedArray.getBoolean(
                R.styleable.ParticleAnimationView_ab_particleConnectLines,
                AnimationConstants.DEFAULT_PARTICLE_CONNECT_LINES
            )

            connectionDistance = typedArray.getDimension(
                R.styleable.ParticleAnimationView_ab_particleConnectionDistance,
                dpToPx(AnimationConstants.DEFAULT_PARTICLE_CONNECTION_DISTANCE)
            )

            particleAlpha = typedArray.getFloat(
                R.styleable.ParticleAnimationView_ab_particleAlpha,
                AnimationConstants.DEFAULT_PARTICLE_ALPHA
            )

            autoStart = typedArray.getBoolean(
                R.styleable.ParticleAnimationView_ab_particleAutoStart,
                AnimationConstants.DEFAULT_PARTICLE_AUTO_START
            )

            typedArray.recycle()
        }
    }

    private fun setupPaints() {
        particlePaint.color = particleColor
        particlePaint.alpha = (particleAlpha * 255).toInt()
        particlePaint.style = Paint.Style.FILL

        linePaint.color = particleColor
        linePaint.alpha = ((particleAlpha * 0.3f) * 255).toInt()
        linePaint.strokeWidth = dpToPx(1f)
        linePaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeParticles()
    }

    private fun initializeParticles() {
        particles.clear()
        val w = width.toFloat()
        val h = height.toFloat()

        if (w == 0f || h == 0f) return

        repeat(particleCount) {
            particles.add(
                Particle(
                    x = Random.nextFloat() * w,
                    y = Random.nextFloat() * h,
                    size = Random.nextFloat() * (maxSize - minSize) + minSize,
                    speedX = getSpeedX(),
                    speedY = getSpeedY()
                )
            )
        }
    }

    private fun getSpeedX(): Float {
        return when (direction) {
            AnimationConstants.ParticleDirection.LEFT -> -Random.nextFloat() * 2f * speedMultiplier
            AnimationConstants.ParticleDirection.RIGHT -> Random.nextFloat() * 2f * speedMultiplier
            AnimationConstants.ParticleDirection.RANDOM -> (Random.nextFloat() - 0.5f) * 2f * speedMultiplier
            else -> (Random.nextFloat() - 0.5f) * 0.5f * speedMultiplier
        }
    }

    private fun getSpeedY(): Float {
        return when (direction) {
            AnimationConstants.ParticleDirection.UP -> -Random.nextFloat() * 2f * speedMultiplier
            AnimationConstants.ParticleDirection.DOWN -> Random.nextFloat() * 2f * speedMultiplier
            AnimationConstants.ParticleDirection.RANDOM -> (Random.nextFloat() - 0.5f) * 2f * speedMultiplier
            else -> (Random.nextFloat() - 0.5f) * 0.5f * speedMultiplier
        }
    }

    override fun updateAnimation(deltaTime: Long) {
        val w = width.toFloat()
        val h = height.toFloat()

        particles.forEach { particle ->
            particle.x += particle.speedX * (deltaTime / 16f)
            particle.y += particle.speedY * (deltaTime / 16f)

            // Wrap around screen edges
            if (particle.x < -particle.size) particle.x = w + particle.size
            if (particle.x > w + particle.size) particle.x = -particle.size
            if (particle.y < -particle.size) particle.y = h + particle.size
            if (particle.y > h + particle.size) particle.y = -particle.size
        }
    }

    override fun drawAnimation(canvas: Canvas) {
        // Draw connection lines first (behind particles)
        if (connectLines) {
            drawConnections(canvas)
        }

        // Draw particles
        particles.forEach { particle ->
            canvas.drawCircle(particle.x, particle.y, particle.size, particlePaint)
        }
    }

    private fun drawConnections(canvas: Canvas) {
        for (i in particles.indices) {
            for (j in i + 1 until particles.size) {
                val p1 = particles[i]
                val p2 = particles[j]
                val distance = sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))

                if (distance < connectionDistance) {
                    val alpha = (1f - distance / connectionDistance) * particleAlpha * 0.3f
                    linePaint.alpha = (alpha * 255).toInt()
                    canvas.drawLine(p1.x, p1.y, p2.x, p2.y, linePaint)
                }
            }
        }
    }

    override fun shouldAutoStart(): Boolean = autoStart

    /**
     * Update particle count dynamically
     */
    fun setParticleCount(count: Int) {
        particleCount = count
        initializeParticles()
        invalidate()
    }

    /**
     * Data class representing a single particle
     */
    private data class Particle(
        var x: Float,
        var y: Float,
        val size: Float,
        val speedX: Float,
        val speedY: Float
    )
}