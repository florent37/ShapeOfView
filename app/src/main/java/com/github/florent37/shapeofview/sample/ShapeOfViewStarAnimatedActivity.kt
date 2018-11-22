package com.github.florent37.shapeofview.sample

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.florent37.shapeofview.shapes.ArcView
import com.github.florent37.shapeofview.shapes.RoundRectView

class ShapeOfViewStarAnimatedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shape_of_view_star_wars_animated)

        val arcLayout = findViewById<ArcView>(R.id.arcLayout)
        if (arcLayout != null) {
            ValueAnimator.ofFloat(0f, -200f, 200f).apply {
                addUpdateListener { animation -> arcLayout.arcHeight = (animation.animatedValue as Float).toInt() }
                duration = 5000
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
        }
    }
}
