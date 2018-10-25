package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.manager.ClipPathManager;


public class TriangleView extends ShapeOfView {
    private float percentBottom = 0.5f;
    private float percentLeft = 0f;
    private float percentRight = 0f;

    public TriangleView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public TriangleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TriangleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TriangleView);
            percentBottom = attributes.getFloat(R.styleable.TriangleView_shape_triangle_percentBottom, percentBottom);
            percentLeft = attributes.getFloat(R.styleable.TriangleView_shape_triangle_percentLeft, percentLeft);
            percentRight = attributes.getFloat(R.styleable.TriangleView_shape_triangle_percentRight, percentRight);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                path.moveTo(0, percentLeft * height);
                path.lineTo(percentBottom * width, height);
                path.lineTo(width, percentRight * height);
                path.close();

                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    public float getPercentBottom() {
        return percentBottom;
    }

    public void setPercentBottom(float percentBottom) {
        this.percentBottom = percentBottom;
        requiresShapeUpdate();
    }

    public float getPercentLeft() {
        return percentLeft;
    }

    public void setPercentLeft(float percentLeft) {
        this.percentLeft = percentLeft;
        requiresShapeUpdate();
    }

    public float getPercentRight() {
        return percentRight;
    }

    public void setPercentRight(float percentRight) {
        this.percentRight = percentRight;
        requiresShapeUpdate();
    }
}
