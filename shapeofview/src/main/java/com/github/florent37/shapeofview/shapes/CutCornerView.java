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

public class CutCornerView extends ShapeOfView {

    private final RectF rectF = new RectF();

    private int topLeftCutSize;
    private int topRightCutSize;
    private int bottomRightCutSize;
    private int bottomLeftCutSize;

    public CutCornerView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CutCornerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CutCornerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CutCornerView);
            topLeftCutSize = attributes.getDimensionPixelSize(R.styleable.CutCornerView_shape_cutCorner_topLeftSize, topLeftCutSize);
            topRightCutSize = attributes.getDimensionPixelSize(R.styleable.CutCornerView_shape_cutCorner_topRightSize, topRightCutSize);
            bottomLeftCutSize = attributes.getDimensionPixelSize(R.styleable.CutCornerView_shape_cutCorner_bottomLeftSize, bottomLeftCutSize);
            bottomRightCutSize = attributes.getDimensionPixelSize(R.styleable.CutCornerView_shape_cutCorner_bottomRightSize, bottomRightCutSize);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                rectF.set(0, 0, width, height);
                return generatePath(rectF, topLeftCutSize, topRightCutSize, bottomRightCutSize, bottomLeftCutSize);
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    private Path generatePath(RectF rect, float topLeftDiameter, float topRightDiameter, float bottomRightDiameter, float bottomLeftDiameter) {
        final Path path = new Path();

        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        path.moveTo(rect.left + topLeftDiameter, rect.top);
        path.lineTo(rect.right - topRightDiameter, rect.top);
        path.lineTo(rect.right, rect.top + topRightDiameter);
        path.lineTo(rect.right, rect.bottom - bottomRightDiameter);
        path.lineTo(rect.right - bottomRightDiameter, rect.bottom);
        path.lineTo(rect.left + bottomLeftDiameter, rect.bottom);
        path.lineTo(rect.left, rect.bottom - bottomLeftDiameter);
        path.lineTo(rect.left, rect.top + topLeftDiameter);
        path.lineTo(rect.left + topLeftDiameter, rect.top);
        path.close();

        return path;
    }

    public int getTopLeftCutSize() {
        return topLeftCutSize;
    }

    public void setTopLeftCutSize(int topLeftCutSize) {
        this.topLeftCutSize = topLeftCutSize;
        requiresShapeUpdate();
    }

    public int getTopRightCutSize() {
        return topRightCutSize;
    }

    public void setTopRightCutSize(int topRightCutSize) {
        this.topRightCutSize = topRightCutSize;
        requiresShapeUpdate();
    }

    public int getBottomRightCutSize() {
        return bottomRightCutSize;
    }

    public void setBottomRightCutSize(int bottomRightCutSize) {
        this.bottomRightCutSize = bottomRightCutSize;
        requiresShapeUpdate();
    }

    public int getBottomLeftCutSize() {
        return bottomLeftCutSize;
    }

    public void setBottomLeftCutSize(int bottomLeftCutSize) {
        this.bottomLeftCutSize = bottomLeftCutSize;
        requiresShapeUpdate();
    }
}
