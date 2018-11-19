package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.manager.ClipPathManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DottedEdgesCutCornerView extends ShapeOfView {

    public static final int POSITION_NONE = 0;
    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 4;
    public static final int POSITION_RIGHT = 8;

    private final RectF rectF = new RectF();

    private float topLeftCutSize = 0f;
    private float topRightCutSize = 0f;
    private float bottomRightCutSize = 0f;
    private float bottomLeftCutSize = 0f;

    private int dotEdgePosition;

    private float dotRadius = 0f;
    private float dotSpacing = 0f;

    public DottedEdgesCutCornerView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DottedEdgesCutCornerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DottedEdgesCutCornerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DottedEdgesCutCornerView);
            topLeftCutSize = attributes.getDimensionPixelSize(R.styleable.DottedEdgesCutCornerView_shape_dottedEdgesCutCorner_topLeftSize, (int) topLeftCutSize);
            topRightCutSize = attributes.getDimensionPixelSize(R.styleable.DottedEdgesCutCornerView_shape_dottedEdgesCutCorner_topRightSize, (int) topRightCutSize);
            bottomLeftCutSize = attributes.getDimensionPixelSize(R.styleable.DottedEdgesCutCornerView_shape_dottedEdgesCutCorner_bottomLeftSize, (int) bottomLeftCutSize);
            bottomRightCutSize = attributes.getDimensionPixelSize(R.styleable.DottedEdgesCutCornerView_shape_dottedEdgesCutCorner_bottomRightSize, (int) bottomRightCutSize);
            dotEdgePosition = attributes.getInteger(R.styleable.DottedEdgesCutCornerView_shape_edge_position, POSITION_NONE);
            dotRadius = attributes.getDimensionPixelSize(R.styleable.DottedEdgesCutCornerView_shape_dot_radius, (int) dotRadius);
            dotSpacing = attributes.getDimensionPixelSize(R.styleable.DottedEdgesCutCornerView_shape_dot_spacing, (int) dotSpacing);
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
        if (containsFlag(POSITION_TOP)) {
            int count = 1;
            int x = (int) (rect.left + topLeftDiameter + dotSpacing * count + dotRadius * 2 * (count - 1));
            while (x + dotSpacing + dotRadius * 2 <= rect.right - topRightDiameter) {
                x = (int) (rect.left + topLeftDiameter + dotSpacing * count + dotRadius * 2 * (count - 1));
                path.lineTo(x, rect.top);
                path.quadTo(x + dotRadius, rect.top + dotRadius, x + dotRadius * 2, rect.top);
                count++;
            }
            path.lineTo(rect.right - topRightDiameter, rect.top);
        } else {
            path.lineTo(rect.right - topRightDiameter, rect.top);
        }
        path.lineTo(rect.right, rect.top + topRightDiameter);
        if (containsFlag(POSITION_RIGHT)) {
            //drawing dots starts from the bottom just like the LEFT side so that when using two
            //widgets side by side, their dots positions will match each other
            path.lineTo(rect.right - dotRadius, rect.top + topRightDiameter);
            path.lineTo(rect.right - dotRadius, rect.bottom - bottomRightDiameter);
            path.lineTo(rect.right, rect.bottom - bottomRightDiameter);

            int count = 1;
            int y = (int) (rect.bottom - bottomRightDiameter - dotSpacing * count - dotRadius * 2 * (count - 1));
            while (y - dotSpacing - dotRadius * 2 >= rect.top + topRightDiameter) {
                y = (int) (rect.bottom - bottomRightDiameter - dotSpacing * count - dotRadius * 2 * (count - 1));
                path.lineTo(rect.right, y);
                path.quadTo(rect.right - dotRadius, y - dotRadius, rect.right, y - dotRadius * 2);
                count++;
            }
            path.lineTo(rect.right, rect.top + topRightDiameter);
            path.lineTo(rect.right - dotRadius, rect.top + topRightDiameter);
            path.lineTo(rect.right - dotRadius, rect.bottom - bottomRightDiameter);
            path.lineTo(rect.right, rect.bottom - bottomRightDiameter);
        } else {
            path.lineTo(rect.right, rect.bottom - bottomRightDiameter);
        }
        path.lineTo(rect.right - bottomRightDiameter, rect.bottom);
        if (containsFlag(POSITION_BOTTOM)) {
            int count = 1;
            int x = (int) (rect.right - bottomRightDiameter - dotSpacing * count - dotRadius * 2 * (count - 1));
            while (x - dotSpacing - dotRadius * 2 >= rect.left + bottomLeftDiameter) {
                x = (int) (rect.right - bottomRightDiameter - dotSpacing * count - dotRadius * 2 * (count - 1));
                path.lineTo(x, rect.bottom);
                path.quadTo(x - dotRadius, rect.bottom - dotRadius, x - dotRadius * 2, rect.bottom);
                count++;
            }
            path.lineTo(rect.left + bottomLeftDiameter, rect.bottom);
        } else {
            path.lineTo(rect.left + bottomLeftDiameter, rect.bottom);
        }
        path.lineTo(rect.left, rect.bottom - bottomLeftDiameter);
        if (containsFlag(POSITION_LEFT)) {
            int count = 1;
            int y = (int) (rect.bottom - bottomLeftDiameter - dotSpacing * count - dotRadius * 2 * (count - 1));
            while (y - dotSpacing - dotRadius * 2 >= rect.top + topLeftDiameter) {
                y = (int) (rect.bottom - bottomLeftDiameter - dotSpacing * count - dotRadius * 2 * (count - 1));
                path.lineTo(rect.left, y);
                path.quadTo(rect.left + dotRadius, y - dotRadius, rect.left, y - dotRadius * 2);
                count++;
            }
            path.lineTo(rect.left, rect.top + topLeftDiameter);
        } else {
            path.lineTo(rect.left, rect.top + topLeftDiameter);
        }
        path.lineTo(rect.left + topLeftDiameter, rect.top);
        path.close();

        return path;
    }

    private boolean containsFlag(int positionFlag) {
        return (dotEdgePosition | positionFlag) == dotEdgePosition;
    }

    public float getTopLeftCutSize() {
        return topLeftCutSize;
    }

    public void setTopLeftCutSize(float topLeftCutSize) {
        this.topLeftCutSize = topLeftCutSize;
        requiresShapeUpdate();
    }

    public float getTopLeftCutSizeDp() {
        return pxToDp(getTopLeftCutSize());
    }

    public void setTopLeftCutSizeDp(float topLeftCutSize) {
        setTopLeftCutSize(dpToPx(topLeftCutSize));
    }

    public float getTopRightCutSize() {
        return topRightCutSize;
    }

    public void setTopRightCutSize(float topRightCutSize) {
        this.topRightCutSize = topRightCutSize;
        requiresShapeUpdate();
    }

    public float getTopRightCutSizeDp() {
        return pxToDp(getTopRightCutSize());
    }

    public void setTopRightCutSizeDp(float topRightCutSize) {
        setTopRightCutSize(dpToPx(topRightCutSize));
    }

    public float getBottomRightCutSize() {
        return bottomRightCutSize;
    }

    public void setBottomRightCutSize(float bottomRightCutSize) {
        this.bottomRightCutSize = bottomRightCutSize;
        requiresShapeUpdate();
    }

    public float getBottomRightCutSizeDp() {
        return pxToDp(getBottomRightCutSize());
    }

    public void setBottomRightCutSizeDp(float bottomRightCutSize) {
        this.setBottomRightCutSize(dpToPx(bottomRightCutSize));
    }

    public float getBottomLeftCutSize() {
        return bottomLeftCutSize;
    }

    public void setBottomLeftCutSize(float bottomLeftCutSize) {
        this.bottomLeftCutSize = bottomLeftCutSize;
        requiresShapeUpdate();
    }

    public float getBottomLeftCutSizeDp() {
        return pxToDp(getBottomLeftCutSize());
    }

    public void setBottomLeftCutSizeDp(float bottomLeftCutSize) {
        setBottomLeftCutSize(dpToPx(bottomLeftCutSize));
    }

    public int getDotEdgePosition() {
        return dotEdgePosition;
    }

    public void addDotEdgePosition(int dotEdgePosition) {
        this.dotEdgePosition |= dotEdgePosition;
        requiresShapeUpdate();
    }

    public float getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(float dotRadius) {
        this.dotRadius = dotRadius;
        requiresShapeUpdate();
    }

    public float getDotRadiusDp() {
        return pxToDp(getDotRadius());
    }

    public void setDotRadiusDp(float dotRadius) {
        setDotRadius(dpToPx(dotRadius));
    }

    public float getDotSpacing() {
        return dotSpacing;
    }

    public void setDotSpacing(float dotSpacing) {
        this.dotSpacing = dotSpacing;
        requiresShapeUpdate();
    }

    public float getDotSpacingDp() {
        return pxToDp(dotSpacing);
    }

    public void setDotSpacingDp(float dotSpacing) {
        setDotRadius(dpToPx(dotSpacing));
    }
}

