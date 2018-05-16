package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.manager.ClipPathManager;

public class RoundRectView extends ShapeOfView {

    private final RectF rectF = new RectF();
    private int topLeftRadius;
    private int topRightRadius;
    private int bottomRightRadius;
    private int bottomLeftRadius;

    //region border
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF borderRectF = new RectF();
    private final Path borderPath = new Path();
    @ColorInt
    private int borderColor = Color.WHITE;
    private int borderWidthPx = 0;
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
            topLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_topLeftRadius, topLeftRadius);
            topRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_topRightRadius, topRightRadius);
            bottomLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_bottomLeftRadius, bottomLeftRadius);
            bottomRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_bottomRightRadius, bottomRightRadius);
            borderColor = attributes.getColor(R.styleable.RoundRectView_shape_roundRect_borderColor, borderColor);
            borderWidthPx = attributes.getDimensionPixelSize(R.styleable.RoundRectView_shape_roundRect_borderWidth, borderWidthPx);
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
        });
    }

    protected float limitSize(float from, final float width, final float height){
        return Math.min(from, Math.min(width, height));
    }


    @Override
    public void invalidate() {
        super.invalidate();
        borderRectF.set(borderWidthPx / 2f, borderWidthPx / 2f, getWidth() - borderWidthPx / 2f, getHeight() - borderWidthPx / 2f);

        borderPath.set(generatePath(borderRectF,
                topLeftRadius,
                topRightRadius,
                bottomRightRadius,
                bottomLeftRadius
        ));
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
        return generatePath(true, rect, topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius);
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

    public int getTopLeftRadius() {
        return topLeftRadius;
    }

    public void setTopLeftRadius(int topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
        postInvalidate();
    }

    public int getTopRightRadius() {
        return topRightRadius;
    }

    public void setTopRightRadius(int topRightRadius) {
        this.topRightRadius = topRightRadius;
        postInvalidate();
    }

    public int getBottomRightRadius() {
        return bottomRightRadius;
    }

    public void setBottomRightRadius(int bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
        postInvalidate();
    }

    public int getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public void setBottomLeftRadius(int bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
        postInvalidate();
    }


    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        postInvalidate();
    }

    public int getBorderWidthPx() {
        return borderWidthPx;
    }

    public void setBorderWidthPx(int borderWidthPx) {
        this.borderWidthPx = borderWidthPx;
        postInvalidate();
    }
}
