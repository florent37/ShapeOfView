package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.manager.ClipPathManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundRectView extends ShapeOfView {

    private final RectF rectF = new RectF();
    //region border
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF borderRectF = new RectF();
    private final Path borderPath = new Path();
    private float topLeftRadius = 0f;
    private float topRightRadius = 0f;
    private float bottomRightRadius = 0f;
    private float bottomLeftRadius = 0f;
    @ColorInt
    private int borderColor = Color.WHITE;

    private float borderWidthPx = 0f;
    //endregion

    public RoundRectView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RoundRectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundRectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundRectView);
            topLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_topLeftRadius, (int) topLeftRadius);
            topRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_topRightRadius, (int) topRightRadius);
            bottomLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_bottomLeftRadius, (int) bottomLeftRadius);
            bottomRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_bottomRightRadius, (int) bottomRightRadius);
            borderColor = attributes.getColor(R.styleable.RoundRectView_shape_roundRect_borderColor, borderColor);
            borderWidthPx = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_borderWidth, (int) borderWidthPx);
            attributes.recycle();
        }
        borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                rectF.set(0, 0, width, height);
                return generatePath(rectF,
                        limitSize(topLeftRadius, width, height),
                        limitSize(topRightRadius, width, height),
                        limitSize(bottomRightRadius, width, height),
                        limitSize(bottomLeftRadius, width, height)
                );
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    protected float limitSize(float from, final float width, final float height) {
        return Math.min(from, Math.min(width, height));
    }

    @Override
    public void requiresShapeUpdate() {
        borderRectF.set(borderWidthPx / 2f, borderWidthPx / 2f, getWidth() - borderWidthPx / 2f, getHeight() - borderWidthPx / 2f);

        borderPath.set(generatePath(borderRectF,
                topLeftRadius,
                topRightRadius,
                bottomRightRadius,
                bottomLeftRadius
        ));
        super.requiresShapeUpdate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (borderWidthPx > 0) {
            borderPaint.setStrokeWidth(borderWidthPx);
            borderPaint.setColor(borderColor);
            canvas.drawPath(borderPath, borderPaint);
        }
    }

    private Path generatePath(RectF rect, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        return generatePath(false, rect, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
    }

    private Path generatePath(boolean useBezier, RectF rect, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        final Path path = new Path();

        final float left = rect.left;
        final float top = rect.top;
        final float bottom = rect.bottom;
        final float right = rect.right;

        final float minSize = Math.min(rect.width() / 2f, rect.height() / 2f);

        topLeftRadius = topLeftRadius < 0 ? 0 : topLeftRadius;
        topRightRadius = topRightRadius < 0 ? 0 : topRightRadius;
        bottomLeftRadius = bottomLeftRadius < 0 ? 0 : bottomLeftRadius;
        bottomRightRadius = bottomRightRadius < 0 ? 0 : bottomRightRadius;

        if (topLeftRadius > minSize) {
            topLeftRadius = minSize;
        }
        if (topRightRadius > minSize) {
            topRightRadius = minSize;
        }
        if (bottomLeftRadius > minSize) {
            bottomLeftRadius = minSize;
        }
        if (bottomRightRadius > minSize) {
            bottomRightRadius = minSize;
        }

        path.moveTo(left + topLeftRadius, top);
        path.lineTo(right - topRightRadius, top);

        //float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean forceMoveTo
        if (useBezier) {
            path.quadTo(right, top, right, top + topRightRadius);
        } else {
            path.arcTo(new RectF(right - topRightRadius * 2f, top, right, top + topRightRadius * 2f), -90, 90);
        }
        path.lineTo(right, bottom - bottomRightRadius);
        if (useBezier) {
            path.quadTo(right, bottom, right - bottomRightRadius, bottom);
        } else {
            path.arcTo(new RectF(right - bottomRightRadius * 2f, bottom - bottomRightRadius * 2f, right, bottom), 0, 90);
        }
        path.lineTo(left + bottomLeftRadius, bottom);
        if (useBezier) {
            path.quadTo(left, bottom, left, bottom - bottomLeftRadius);
        } else {
            path.arcTo(new RectF(left, bottom - bottomLeftRadius * 2f, left + bottomLeftRadius * 2f, bottom), 90, 90);
        }
        path.lineTo(left, top + topLeftRadius);
        if (useBezier) {
            path.quadTo(left, top, left + topLeftRadius, top);
        } else {
            path.arcTo(new RectF(left, top, left + topLeftRadius * 2f, top + topLeftRadius * 2f), 180, 90);
        }
        path.close();

        return path;
    }

    public float getTopLeftRadiusPx() {
        return topLeftRadius;
    }

    public void setTopLeftRadiusPx(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        requiresShapeUpdate();
    }

    public void setTopLeftRadiusDp(float topLeftRadius) {
        setTopLeftRadiusPx(dpToPx(topLeftRadius));
    }

    public float getTopRightRadiusPx() {
        return topRightRadius;
    }

    public void setTopRightRadiusPx(float topRightRadius) {
        this.topRightRadius = topRightRadius;
        requiresShapeUpdate();
    }

    public void setTopRightRadiusDp(float topRightRadius) {
        setTopRightRadiusPx(dpToPx(topRightRadius));
    }

    public float getBottomRightRadiusPx() {
        return bottomRightRadius;
    }

    public void setBottomRightRadiusPx(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
        requiresShapeUpdate();
    }
    public void setBottomRightRadiusDp(float bottomRightRadius) {
        setBottomRightRadiusPx(dpToPx(bottomRightRadius));
    }
    public float getBottomLeftRadiusPx() {
        return bottomLeftRadius;
    }

    public void setBottomLeftRadiusPx(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
        requiresShapeUpdate();
    }
    public void setBottomLeftRadiusDp(float bottomLeftRadius) {
        setBottomLeftRadiusPx(dpToPx(bottomLeftRadius));
    }

    public float getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        requiresShapeUpdate();
    }

    public float getBorderWidthPx() {
        return borderWidthPx;
    }

    public void setBorderWidthPx(float borderWidthPx) {
        this.borderWidthPx = borderWidthPx;
        requiresShapeUpdate();
    }

    public void setBorderWidthDp(float borderWidth) {
        setBorderWidthPx(dpToPx(borderWidth));
    }
}