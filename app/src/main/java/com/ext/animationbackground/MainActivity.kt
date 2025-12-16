package com.ext.animationbackground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ext.animation_background.views.ButtonAnimationView
import com.ext.animation_background.views.GradientAnimationView
import com.ext.animation_background.views.ParticleAnimationView
import com.ext.animation_background.views.ShapeMorphingAnimationView
import com.ext.animation_background.views.WaveAnimationView

/**
 * MainActivity - Demonstrates all animation types
 *
 * NOTE: All customization is done via XML attributes.
 * This Activity only handles lifecycle management.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var gradientAnimation: GradientAnimationView
    private lateinit var particleAnimation: ParticleAnimationView
    private lateinit var waveAnimation: WaveAnimationView
    private lateinit var shapeMorphingAnimation: ShapeMorphingAnimationView
    private lateinit var buttonAnimation: ButtonAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize animation views
        initializeViews()
    }

    private fun initializeViews() {
        // Find all animation views
        gradientAnimation = findViewById(R.id.gradientAnimation)
        particleAnimation = findViewById(R.id.particleAnimation)
        waveAnimation = findViewById(R.id.waveAnimation)
        shapeMorphingAnimation = findViewById(R.id.shapeMorphingAnimation)
        buttonAnimation = findViewById(R.id.buttonAnimation)

        // All animations are configured via XML attributes
        // They auto-start based on ab_*AutoStart attribute
        // No additional code needed!
    }

    override fun onPause() {
        super.onPause()
        // Pause all animations to save resources
        pauseAllAnimations()
    }

    override fun onResume() {
        super.onResume()
        // Resume all animations
        resumeAllAnimations()
    }

    private fun pauseAllAnimations() {
        gradientAnimation.pauseAnimation()
        particleAnimation.pauseAnimation()
        waveAnimation.pauseAnimation()
        shapeMorphingAnimation.pauseAnimation()
        buttonAnimation.pauseAnimation()
    }

    private fun resumeAllAnimations() {
        gradientAnimation.resumeAnimation()
        particleAnimation.resumeAnimation()
        waveAnimation.resumeAnimation()
        shapeMorphingAnimation.resumeAnimation()
        buttonAnimation.resumeAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop all animations and clean up
        stopAllAnimations()
    }

    private fun stopAllAnimations() {
        gradientAnimation.stopAnimation()
        particleAnimation.stopAnimation()
        waveAnimation.stopAnimation()
        shapeMorphingAnimation.stopAnimation()
        buttonAnimation.stopAnimation()
    }

    // ============================================
    // OPTIONAL: Programmatic Control Examples
    // ============================================

    /**
     * Example: Change gradient colors programmatically
     * (Usually not needed - prefer XML configuration)
     */
    private fun changeGradientColors() {
        gradientAnimation.setGradientColors(
            start = android.graphics.Color.parseColor("#FF6B6B"),
            end = android.graphics.Color.parseColor("#4ECDC4")
        )
    }

    /**
     * Example: Change particle count programmatically
     * (Usually not needed - prefer XML configuration)
     */
    private fun changeParticleCount() {
        particleAnimation.setParticleCount(100)
    }

    /**
     * Example: Change wave colors programmatically
     * (Usually not needed - prefer XML configuration)
     */
    private fun changeWaveColors() {
        waveAnimation.setWaveColors(
            primary = android.graphics.Color.parseColor("#FF6B6B"),
            secondary = android.graphics.Color.parseColor("#FFD93D")
        )
    }

    /**
     * Example: Change shape colors programmatically
     * (Usually not needed - prefer XML configuration)
     */
    private fun changeShapeColors() {
        shapeMorphingAnimation.setShapeColors(
            primary = android.graphics.Color.parseColor("#6BCB77"),
            secondary = android.graphics.Color.parseColor("#4D96FF")
        )
    }

    /**
     * Example: Change button border color programmatically
     * (Usually not needed - prefer XML configuration)
     */
    private fun changeButtonBorderColor() {
        buttonAnimation.setBorderColor(android.graphics.Color.parseColor("#FF6B6B"))
    }
}