package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.manager.ClipPathManager;

public class RoundRectView extends ShapeOfView {

    private final RectF rectF = new RectF();
    //private final RectF borderRectF = new RectF();
    //private final Path borderPath = new Path();
    //private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int topLeftRadius;
    private int topRightRadius;
    private int bottomRightRadius;
    private int bottomLeftRadius;
    //@ColorInt
    //private int borderColor = Color.WHITE;
    //private int borderWidthPx = 0;

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
            topLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_topLeftRadius, topLeftRadius);
            topRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_topRightRadius, topRightRadius);
            bottomLeftRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_bottomLeftRadius, bottomLeftRadius);
            bottomRightRadius = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_bottomRightRadius, bottomRightRadius);
           // borderColor = attributes.getColor(R.styleable.RoundRectView_roundRect_borderColor, borderColor);
           // borderWidthPx = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_bottomRightDiameter, bottomRightDiameter);
            attributes.recycle();
        }
        //borderPaint.setStyle(Paint.Style.STROKE);
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

    /*
    @Override
    public void invalidate() {
        super.invalidate();
        borderRectF.set(borderWidthPx / 2f, borderWidthPx / 2f, getHeight() - borderWidthPx / 2f, getWidth() - borderWidthPx / 2f);
        borderPath.set(generatePath(borderRectF, topLeftDiameter, topRightDiameter, bottomRightDiameter, bottomLeftDiameter));
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
    */

    private Path generatePath(RectF rect, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        final Path path = new Path();

        topLeftRadius = topLeftRadius < 0 ? 0 : topLeftRadius;
        topRightRadius = topRightRadius < 0 ? 0 : topRightRadius;
        bottomLeftRadius = bottomLeftRadius < 0 ? 0 : bottomLeftRadius;
        bottomRightRadius = bottomRightRadius < 0 ? 0 : bottomRightRadius;

        path.moveTo(rect.left + topLeftRadius, rect.top);
        path.lineTo(rect.right - topRightRadius, rect.top);
        path.quadTo(rect.right, rect.top, rect.right, rect.top + topRightRadius);
        path.lineTo(rect.right, rect.bottom - bottomRightRadius);
        path.quadTo(rect.right, rect.bottom, rect.right - bottomRightRadius, rect.bottom);
        path.lineTo(rect.left + bottomLeftRadius, rect.bottom);
        path.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - bottomLeftRadius);
        path.lineTo(rect.left, rect.top + topLeftRadius);
        path.quadTo(rect.left, rect.top, rect.left + topLeftRadius, rect.top);
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

    /*
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
    */
}
