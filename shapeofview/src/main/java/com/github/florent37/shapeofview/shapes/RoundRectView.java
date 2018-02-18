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
    private int topLeftDiameter;
    private int topRightDiameter;
    private int bottomRightDiameter;
    private int bottomLeftDiameter;
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
            topLeftDiameter = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_topLeftDiameter, topLeftDiameter);
            topRightDiameter = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_topRightDiameter, topRightDiameter);
            bottomLeftDiameter = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_bottomLeftDiameter, bottomLeftDiameter);
            bottomRightDiameter = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_bottomRightDiameter, bottomRightDiameter);
           // borderColor = attributes.getColor(R.styleable.RoundRectView_roundRect_borderColor, borderColor);
           // borderWidthPx = attributes.getDimensionPixelSize(R.styleable.RoundRectView_roundRect_bottomRightDiameter, bottomRightDiameter);
            attributes.recycle();
        }
        //borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                rectF.set(0, 0, width, height);
                return generatePath(rectF, topLeftDiameter, topRightDiameter, bottomRightDiameter, bottomLeftDiameter);
            }
        });
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

    private Path generatePath(RectF rect, float topLeftDiameter, float topRightDiameter, float bottomRightDiameter, float bottomLeftDiameter) {
        final Path path = new Path();

        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        path.moveTo(rect.left + topLeftDiameter, rect.top);
        path.lineTo(rect.right - topRightDiameter, rect.top);
        path.quadTo(rect.right, rect.top, rect.right, rect.top + topRightDiameter);
        path.lineTo(rect.right, rect.bottom - bottomRightDiameter);
        path.quadTo(rect.right, rect.bottom, rect.right - bottomRightDiameter, rect.bottom);
        path.lineTo(rect.left + bottomLeftDiameter, rect.bottom);
        path.quadTo(rect.left, rect.bottom, rect.left, rect.bottom - bottomLeftDiameter);
        path.lineTo(rect.left, rect.top + topLeftDiameter);
        path.quadTo(rect.left, rect.top, rect.left + topLeftDiameter, rect.top);
        path.close();

        return path;
    }

    public int getTopLeftDiameter() {
        return topLeftDiameter;
    }

    public void setTopLeftDiameter(int topLeftDiameter) {
        this.topLeftDiameter = topLeftDiameter;
        postInvalidate();
    }

    public int getTopRightDiameter() {
        return topRightDiameter;
    }

    public void setTopRightDiameter(int topRightDiameter) {
        this.topRightDiameter = topRightDiameter;
        postInvalidate();
    }

    public int getBottomRightDiameter() {
        return bottomRightDiameter;
    }

    public void setBottomRightDiameter(int bottomRightDiameter) {
        this.bottomRightDiameter = bottomRightDiameter;
        postInvalidate();
    }

    public int getBottomLeftDiameter() {
        return bottomLeftDiameter;
    }

    public void setBottomLeftDiameter(int bottomLeftDiameter) {
        this.bottomLeftDiameter = bottomLeftDiameter;
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
