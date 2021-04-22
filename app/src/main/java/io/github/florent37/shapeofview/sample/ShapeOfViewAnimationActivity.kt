package io.github.florent37.shapeofview.sample

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.florent37.shapeofview.sample.R
import io.github.florent37.shapeofview.shapes.RoundRectView

class ShapeOfViewAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shape_of_view_activity_anim)

        val roundRect = findViewById<RoundRectView>(R.id.roundRect)
        if (roundRect != null) {
            ValueAnimator.ofFloat(0f, 200f, 0f).apply {
                addUpdateListener { animation -> roundRect.bottomLeftRadius = (animation.animatedValue as Float) }
                duration = 800
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
        }
    }
}
