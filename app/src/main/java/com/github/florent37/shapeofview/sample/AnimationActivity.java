package com.github.florent37.shapeofview.sample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.florent37.shapeofview.shapes.RoundRectView;

import java.util.ArrayList;
import java.util.List;

public class AnimationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        final RoundRectView roundRect = findViewById(R.id.roundRect);
        if (roundRect != null) {
            final List<ValueAnimator> animators = new ArrayList<>();

            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 200, 0);
            valueAnimator.addUpdateListener(animation -> {
                roundRect.setBottomLeftRadius((int) (float) animation.getAnimatedValue());
            });
            animators.add(valueAnimator);

            //animators.add(ObjectAnimator.ofFloat(roundRect, View.SCALE_X, 1f, 0.5f, 1f));
            //animators.add(ObjectAnimator.ofFloat(roundRect, View.SCALE_Y, 1f, 0.5f, 1f));

            for (ValueAnimator animator : animators) {
                animator.setDuration(800);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.start();
            }
        }
    }
}
