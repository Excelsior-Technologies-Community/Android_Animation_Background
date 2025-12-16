package com.ext.animation_background.utils

import android.graphics.Color

/**
 * Constants used across all animation views
 */
object AnimationConstants {

    // Gradient Animation Defaults
    val DEFAULT_GRADIENT_START_COLOR = Color.parseColor("#667eea")
    val DEFAULT_GRADIENT_END_COLOR = Color.parseColor("#764ba2")
    const val DEFAULT_GRADIENT_DURATION = 3000
    const val DEFAULT_GRADIENT_AUTO_START = true
    const val DEFAULT_GRADIENT_REVERSE = true

    // Particle Animation Defaults
    const val DEFAULT_PARTICLE_COUNT = 50
    const val DEFAULT_PARTICLE_MIN_SIZE = 2f // dp
    const val DEFAULT_PARTICLE_MAX_SIZE = 6f // dp
    val DEFAULT_PARTICLE_COLOR = Color.parseColor("#FFFFFF")
    const val DEFAULT_PARTICLE_SPEED = 1.0f
    const val DEFAULT_PARTICLE_CONNECT_LINES = false
    const val DEFAULT_PARTICLE_CONNECTION_DISTANCE = 100f // dp
    const val DEFAULT_PARTICLE_ALPHA = 0.8f
    const val DEFAULT_PARTICLE_AUTO_START = true

    // Wave Animation Defaults
    val DEFAULT_WAVE_PRIMARY_COLOR = Color.parseColor("#4A90E2")
    val DEFAULT_WAVE_SECONDARY_COLOR = Color.parseColor("#7EC8E3")
    const val DEFAULT_WAVE_COUNT = 3
    const val DEFAULT_WAVE_AMPLITUDE = 40f // dp
    const val DEFAULT_WAVE_FREQUENCY = 2.0f
    const val DEFAULT_WAVE_SPEED = 1.0f
    const val DEFAULT_WAVE_ALPHA = 0.5f
    const val DEFAULT_WAVE_AUTO_START = true

    // Shape Morphing Defaults
    val DEFAULT_SHAPE_PRIMARY_COLOR = Color.parseColor("#A855F7")
    val DEFAULT_SHAPE_SECONDARY_COLOR = Color.parseColor("#EC4899")
    const val DEFAULT_SHAPE_COUNT = 3
    const val DEFAULT_SHAPE_MIN_SIZE = 50f // dp
    const val DEFAULT_SHAPE_MAX_SIZE = 150f // dp
    const val DEFAULT_SHAPE_MORPH_DURATION = 4000
    const val DEFAULT_SHAPE_ENABLE_ROTATION = true
    const val DEFAULT_SHAPE_ROTATION_SPEED = 1.0f
    const val DEFAULT_SHAPE_ALPHA = 0.6f
    const val DEFAULT_SHAPE_BLUR_RADIUS = 30f // dp
    const val DEFAULT_SHAPE_AUTO_START = true

    // Button Animation Defaults
    val DEFAULT_BUTTON_BORDER_COLOR = Color.parseColor("#FFFFFF")
    const val DEFAULT_BUTTON_BORDER_WIDTH = 2f // dp
    const val DEFAULT_BUTTON_DASH_LENGTH = 10f // dp
    const val DEFAULT_BUTTON_DASH_GAP = 5f // dp
    const val DEFAULT_BUTTON_ANIMATION_SPEED = 1.0f
    const val DEFAULT_BUTTON_CORNER_RADIUS = 12f // dp
    const val DEFAULT_BUTTON_AUTO_START = true

    // Animation Frame Rate
    const val TARGET_FPS = 60
    const val FRAME_DELAY = 1000L / TARGET_FPS

    // Direction Enums
    enum class GradientDirection {
        HORIZONTAL, VERTICAL, DIAGONAL_TL_BR, DIAGONAL_TR_BL
    }

    enum class ParticleDirection {
        RANDOM, UP, DOWN, LEFT, RIGHT
    }

    enum class WaveDirection {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    enum class ButtonDirection {
        CLOCKWISE, COUNTER_CLOCKWISE
    }
}