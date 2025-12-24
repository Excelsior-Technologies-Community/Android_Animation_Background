# Animation Background Library

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-21%2B-orange.svg)](#)
[![](https://jitpack.io/v/Excelsior-Technologies-Community/AnimationBackgroundKit.svg)](https://jitpack.io/#Excelsior-Technologies-Community/AnimationBackgroundKit)

**Animation Background Library** is a production-ready Android library that provides stunning, fully customizable animated backgrounds with zero boilerplate. Create professional animated UI elements through simple XML attributes - no custom code required!

---

## 📸 Preview

<img src="app/src/main/assets/Video.gif"
       alt="Animation Background Library Demo" 
      height="320"/>

---

## ✨ Features

- **5 Animation Types**: Gradient, Particle, Wave, Shape Morphing, and Button animations
- **Pure XML Configuration**: No code needed - customize everything through XML attributes
- **60 FPS Performance**: Optimized animations with smooth 60 FPS rendering
- **Lifecycle Aware**: Automatically pauses/resumes with activity lifecycle
- **Memory Efficient**: Smart resource management and cleanup
- **Runtime Customization**: Optional programmatic control for dynamic changes
- **Material Design Ready**: Works seamlessly with Material Design components
- **Production Ready**: Battle-tested with comprehensive lifecycle management
- **Lightweight**: Minimal overhead using native Canvas APIs

---

## 📦 Installation

**Step 1:** Add JitPack repository to your root `build.gradle`:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2:** Add dependency to your app module's `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.Excelsior-Technologies-Community:AnimationBackgroundKit:1.0.0'
}
```

---

## 🚀 Usage

### 1️⃣ Gradient Animation

Perfect for: Login screens, splash screens, professional UI

```xml
<com.ext.animation_background.views.GradientAnimationView
    android:id="@+id/gradientAnimation"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:ab_gradientStartColor="#667eea"
    app:ab_gradientEndColor="#764ba2"
    app:ab_gradientMiddleColor="#F093FB"
    app:ab_gradientDuration="3000"
    app:ab_gradientDirection="diagonal_tl_br"
    app:ab_gradientAutoStart="true"
    app:ab_gradientReverse="true" />
```

### 2️⃣ Particle Animation

Perfect for: Music apps, gaming dashboards, tech-themed UI

```xml
<com.ext.animation_background.views.ParticleAnimationView
    android:id="@+id/particleAnimation"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#0a0a23"
    app:ab_particleCount="60"
    app:ab_particleMinSize="2dp"
    app:ab_particleMaxSize="5dp"
    app:ab_particleColor="#00D9FF"
    app:ab_particleSpeed="1.2"
    app:ab_particleDirection="random"
    app:ab_particleConnectLines="true"
    app:ab_particleConnectionDistance="120dp"
    app:ab_particleAlpha="0.9"
    app:ab_particleAutoStart="true" />
```

### 3️⃣ Wave Animation

Perfect for: Meditation apps, fitness apps, weather UI

```xml
<com.ext.animation_background.views.WaveAnimationView
    android:id="@+id/waveAnimation"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#1a237e"
    app:ab_wavePrimaryColor="#4A90E2"
    app:ab_waveSecondaryColor="#7EC8E3"
    app:ab_waveCount="4"
    app:ab_waveAmplitude="35dp"
    app:ab_waveFrequency="2.5"
    app:ab_waveSpeed="1.0"
    app:ab_waveAlpha="0.6"
    app:ab_waveDirection="left_to_right"
    app:ab_waveAutoStart="true" />
```

### 4️⃣ Shape Morphing Animation

Perfect for: Modern UI, creative apps, portfolio apps

```xml
<com.ext.animation_background.views.ShapeMorphingAnimationView
    android:id="@+id/shapeMorphingAnimation"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#0D1117"
    app:ab_shapePrimaryColor="#A855F7"
    app:ab_shapeSecondaryColor="#EC4899"
    app:ab_shapeCount="4"
    app:ab_shapeMinSize="60dp"
    app:ab_shapeMaxSize="140dp"
    app:ab_shapeMorphDuration="4000"
    app:ab_shapeEnableRotation="true"
    app:ab_shapeRotationSpeed="0.8"
    app:ab_shapeAlpha="0.7"
    app:ab_shapeBlurRadius="25dp"
    app:ab_shapeAutoStart="true" />
```

### 5️⃣ Button Animation

Perfect for: Call-to-action buttons, loading indicators, interactive UI

```xml
<com.ext.animation_background.views.ButtonAnimationView
    android:id="@+id/buttonAnimation"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#0D1117"
    app:ab_buttonBorderColor="#FFFFFF"
    app:ab_buttonBorderWidth="2dp"
    app:ab_buttonDashLength="10dp"
    app:ab_buttonDashGap="5dp"
    app:ab_buttonAnimationSpeed="1.0"
    app:ab_buttonCornerRadius="12dp"
    app:ab_buttonDirection="clockwise"
    app:ab_buttonAutoStart="true" />
```

---

## 💻 Kotlin Programmatic Usage

While XML configuration is recommended, all animations support programmatic customization:

### Activity Lifecycle Management

```kotlin
class MainActivity : AppCompatActivity() {
    
    private lateinit var gradientAnimation: GradientAnimationView
    private lateinit var particleAnimation: ParticleAnimationView
    private lateinit var waveAnimation: WaveAnimationView
    private lateinit var shapeMorphingAnimation: ShapeMorphingAnimationView
    private lateinit var buttonAnimation: ButtonAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize views
        gradientAnimation = findViewById(R.id.gradientAnimation)
        particleAnimation = findViewById(R.id.particleAnimation)
        waveAnimation = findViewById(R.id.waveAnimation)
        shapeMorphingAnimation = findViewById(R.id.shapeMorphingAnimation)
        buttonAnimation = findViewById(R.id.buttonAnimation)
        
        // Animations auto-start based on XML attributes
    }

    override fun onPause() {
        super.onPause()
        // Pause animations to save resources
        gradientAnimation.pauseAnimation()
        particleAnimation.pauseAnimation()
        waveAnimation.pauseAnimation()
        shapeMorphingAnimation.pauseAnimation()
        buttonAnimation.pauseAnimation()
    }

    override fun onResume() {
        super.onResume()
        // Resume animations
        gradientAnimation.resumeAnimation()
        particleAnimation.resumeAnimation()
        waveAnimation.resumeAnimation()
        shapeMorphingAnimation.resumeAnimation()
        buttonAnimation.resumeAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up resources
        gradientAnimation.stopAnimation()
        particleAnimation.stopAnimation()
        waveAnimation.stopAnimation()
        shapeMorphingAnimation.stopAnimation()
        buttonAnimation.stopAnimation()
    }
}
```

### Dynamic Customization (Optional)

```kotlin
// Change gradient colors at runtime
gradientAnimation.setGradientColors(
    start = Color.parseColor("#FF6B6B"),
    end = Color.parseColor("#4ECDC4")
)

// Update particle count dynamically
particleAnimation.setParticleCount(100)

// Change wave colors
waveAnimation.setWaveColors(
    primary = Color.parseColor("#FF6B6B"),
    secondary = Color.parseColor("#FFD93D")
)

// Update shape colors
shapeMorphingAnimation.setShapeColors(
    primary = Color.parseColor("#6BCB77"),
    secondary = Color.parseColor("#4D96FF")
)

// Change button border color
buttonAnimation.setBorderColor(Color.parseColor("#FF6B6B"))

// Control animation speed
buttonAnimation.setAnimationSpeed(2.0f)
```

---

## 🔧 XML Attributes

### Gradient Animation Attributes

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `ab_gradientStartColor` | color | #667eea | Start color of gradient |
| `ab_gradientEndColor` | color | #764ba2 | End color of gradient |
| `ab_gradientMiddleColor` | color | null | Optional middle color for 3-color gradients |
| `ab_gradientDuration` | integer | 3000 | Animation duration in milliseconds |
| `ab_gradientDirection` | enum | horizontal | Direction: horizontal, vertical, diagonal_tl_br, diagonal_tr_bl |
| `ab_gradientAutoStart` | boolean | true | Auto-start animation on view creation |
| `ab_gradientReverse` | boolean | true | Enable reverse animation |

### Particle Animation Attributes

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `ab_particleCount` | integer | 50 | Number of particles |
| `ab_particleMinSize` | dimension | 2dp | Minimum particle size |
| `ab_particleMaxSize` | dimension | 6dp | Maximum particle size |
| `ab_particleColor` | color | #FFFFFF | Particle color |
| `ab_particleSpeed` | float | 1.0 | Speed multiplier |
| `ab_particleDirection` | enum | random | Direction: random, up, down, left, right |
| `ab_particleConnectLines` | boolean | false | Draw connection lines between nearby particles |
| `ab_particleConnectionDistance` | dimension | 100dp | Maximum distance for particle connections |
| `ab_particleAlpha` | float | 0.8 | Particle opacity (0.0 - 1.0) |
| `ab_particleAutoStart` | boolean | true | Auto-start animation |

### Wave Animation Attributes

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `ab_wavePrimaryColor` | color | #4A90E2 | Primary wave color |
| `ab_waveSecondaryColor` | color | #7EC8E3 | Secondary wave color |
| `ab_waveCount` | integer | 3 | Number of waves |
| `ab_waveAmplitude` | dimension | 40dp | Wave height |
| `ab_waveFrequency` | float | 2.0 | Number of complete waves visible |
| `ab_waveSpeed` | float | 1.0 | Speed multiplier |
| `ab_waveAlpha` | float | 0.5 | Wave opacity (0.0 - 1.0) |
| `ab_waveDirection` | enum | left_to_right | Direction: left_to_right, right_to_left |
| `ab_waveAutoStart` | boolean | true | Auto-start animation |

### Shape Morphing Attributes

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `ab_shapePrimaryColor` | color | #A855F7 | Primary shape color |
| `ab_shapeSecondaryColor` | color | #EC4899 | Secondary shape color |
| `ab_shapeCount` | integer | 3 | Number of shapes |
| `ab_shapeMinSize` | dimension | 50dp | Minimum shape size |
| `ab_shapeMaxSize` | dimension | 150dp | Maximum shape size |
| `ab_shapeMorphDuration` | integer | 4000 | Morph duration in milliseconds |
| `ab_shapeEnableRotation` | boolean | true | Enable shape rotation |
| `ab_shapeRotationSpeed` | float | 1.0 | Rotation speed multiplier |
| `ab_shapeAlpha` | float | 0.6 | Shape opacity (0.0 - 1.0) |
| `ab_shapeBlurRadius` | dimension | 30dp | Blur effect radius |
| `ab_shapeAutoStart` | boolean | true | Auto-start animation |

### Button Animation Attributes

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `ab_buttonBorderColor` | color | #FFFFFF | Border color |
| `ab_buttonBorderWidth` | dimension | 2dp | Border width |
| `ab_buttonDashLength` | dimension | 10dp | Length of each dash |
| `ab_buttonDashGap` | dimension | 5dp | Gap between dashes |
| `ab_buttonAnimationSpeed` | float | 1.0 | Animation speed multiplier |
| `ab_buttonCornerRadius` | dimension | 12dp | Corner radius |
| `ab_buttonDirection` | enum | clockwise | Direction: clockwise, counter_clockwise |
| `ab_buttonAutoStart` | boolean | true | Auto-start animation |

---

## 📝 Methods

### Common Methods (All Animation Views)

```kotlin
fun startAnimation()        // Start the animation
fun stopAnimation()         // Stop and clean up animation
fun pauseAnimation()        // Pause animation (can be resumed)
fun resumeAnimation()       // Resume paused animation
```

### Gradient Animation Methods

```kotlin
fun setGradientColors(start: Int, end: Int, middle: Int? = null)  // Set gradient colors
fun setAnimationDuration(durationMs: Int)                         // Set animation duration
```

### Particle Animation Methods

```kotlin
fun setParticleCount(count: Int)  // Update particle count dynamically
```

### Wave Animation Methods

```kotlin
fun setWaveColors(primary: Int, secondary: Int)  // Update wave colors
```

### Shape Morphing Methods

```kotlin
fun setShapeColors(primary: Int, secondary: Int)  // Update shape colors
```

### Button Animation Methods

```kotlin
fun setBorderColor(color: Int)      // Update border color
fun setAnimationSpeed(speed: Float) // Update animation speed
```

---

## 🎨 Best Practices

1. **Use XML Configuration**: Define all properties in XML for better maintainability
2. **Manage Lifecycle**: Always pause animations in `onPause()` to save battery
3. **Choose Appropriate Animations**: Match animation type to your app's theme
4. **Optimize Particle Count**: Use 30-60 particles for best performance
5. **Test on Low-End Devices**: Ensure smooth performance across all devices
6. **Combine with Material Design**: Works seamlessly with CardView and other Material components

---

## 📄 License

```
MIT License

Copyright (c) 2025 Excelsior Technologies

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---
