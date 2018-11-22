package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.manager.ClipPathManager;


public class CircleView extends ShapeOfView {
    private float borderWidthPx = 0f;

    @ColorInt
    private int borderColor = Color.WHITE;

    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CircleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
            borderWidthPx = attributes.getDimensionPixelSize(R.styleable.CircleView_shape_circle_borderWidth, (int) borderWidthPx);
            borderColor = attributes.getColor(R.styleable.CircleView_shape_circle_borderColor, borderColor);
            attributes.recycle();
        }
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                path.addCircle(width/2f, height/2f, Math.min(width/2f, height/2f), Path.Direction.CW);

                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if(borderWidthPx > 0){
            borderPaint.setStrokeWidth(borderWidthPx);
            borderPaint.setColor(borderColor);
            canvas.drawCircle(getWidth()/2f, getHeight()/2f, Math.min((getWidth() - borderWidthPx) /2f, (getHeight() - borderWidthPx) /2f), borderPaint);
        }
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidthPx = borderWidth;
        requiresShapeUpdate();
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        requiresShapeUpdate();
    }

    public void setBorderWidthDp(float borderWidth) {
        setBorderWidth(dpToPx(borderWidth));
    }

    public float getBorderWidth() {
        return borderWidthPx;
    }

    public float getBorderWidthDp() {
        return pxToDp(getBorderWidth());
    }

    public int getBorderColor() {
        return borderColor;
    }
}
