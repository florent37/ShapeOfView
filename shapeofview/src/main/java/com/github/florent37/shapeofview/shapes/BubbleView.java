package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.manager.ClipPathManager;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BubbleView extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;

    @Position
    private int position = POSITION_BOTTOM;

    private float borderRadiusPx;
    private float arrowHeightPx;
    private float arrowWidthPx;

    private float defPositionPer=0.5f;
    private float positionPer;

    public BubbleView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public BubbleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BubbleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.BubbleView);
            borderRadiusPx = attributes.getDimensionPixelSize(R.styleable.BubbleView_shape_bubble_borderRadius, (int) dpToPx(10));
            position = attributes.getInteger(R.styleable.BubbleView_shape_bubble_arrowPosition, position);
            arrowHeightPx = attributes.getDimensionPixelSize(R.styleable.BubbleView_shape_bubble_arrowHeight, (int) dpToPx(10));
            arrowWidthPx = attributes.getDimensionPixelSize(R.styleable.BubbleView_shape_bubble_arrowWidth, (int) dpToPx(10));
            positionPer=attributes.getFloat(R.styleable.BubbleView_arrow_posititon_percent,defPositionPer);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final RectF myRect = new RectF(0, 0, width, height);
                return drawBubble(myRect, borderRadiusPx, borderRadiusPx, borderRadiusPx, borderRadiusPx);
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(@Position int position) {
        this.position = position;
        requiresShapeUpdate();
    }

    public float getBorderRadius() {
        return borderRadiusPx;
    }

    public float getBorderRadiusDp() {
        return pxToDp(getBorderRadius());
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadiusPx = borderRadius;
        requiresShapeUpdate();
    }

    public void setBorderRadiusDp(float borderRadius) {
        this.borderRadiusPx = dpToPx(borderRadius);
        requiresShapeUpdate();
    }

    public float getArrowHeight() {
        return arrowHeightPx;
    }

    public float getArrowHeightDp() {
        return pxToDp(getArrowHeight());
    }

    public void setArrowHeight(float arrowHeight) {
        this.arrowHeightPx = arrowHeight;
        requiresShapeUpdate();
    }

    public void setArrowHeightDp(float arrowHeight) {
        setArrowHeight(dpToPx(arrowHeight));
    }

    public float getArrowWidth() {
        return arrowWidthPx;
    }

    public void setArrowWidth(float arrowWidth) {
        this.arrowWidthPx = arrowWidth;
        requiresShapeUpdate();
    }

    public void setArrowWidthDp(float arrowWidth) {
        setArrowWidth(dpToPx(arrowWidth));
    }


    public void setPositionPer(float positionPer) {
        this.positionPer = positionPer;
        requiresShapeUpdate();
    }

    private Path drawBubble(RectF myRect, float topLeftDiameter, float topRightDiameter, float bottomRightDiameter, float bottomLeftDiameter) {
        final Path path = new Path();

        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        final float spacingLeft = this.position == POSITION_LEFT ? arrowHeightPx : 0;
        final float spacingTop = this.position == POSITION_TOP ? arrowHeightPx : 0;
        final float spacingRight = this.position == POSITION_RIGHT ? arrowHeightPx : 0;
        final float spacingBottom = this.position == POSITION_BOTTOM ? arrowHeightPx : 0;

        final float left = spacingLeft + myRect.left;
        final float top = spacingTop + myRect.top;
        final float right = myRect.right - spacingRight;
        final float bottom = myRect.bottom - spacingBottom;

        final float centerX = (myRect.left + myRect.right) * positionPer;

        path.moveTo(left + topLeftDiameter / 2f, top);
        //LEFT, TOP

        if (position == POSITION_TOP) {
            path.lineTo(centerX - arrowWidthPx, top);
            path.lineTo(centerX, myRect.top);
            path.lineTo(centerX + arrowWidthPx, top);
        }
        path.lineTo(right - topRightDiameter / 2f, top);

        path.quadTo(right, top, right, top + topRightDiameter / 2);
        //RIGHT, TOP

        if (position == POSITION_RIGHT) {
            path.lineTo(right, bottom-(bottom*(1-positionPer))- arrowWidthPx);
            path.lineTo(myRect.right, bottom-(bottom*(1-positionPer)));
            path.lineTo(right, bottom-(bottom*(1-positionPer)) + arrowWidthPx);
        }
        path.lineTo(right, bottom - bottomRightDiameter / 2);

        path.quadTo(right, bottom, right - bottomRightDiameter / 2, bottom);
        //RIGHT, BOTTOM

        if (position == POSITION_BOTTOM) {
            path.lineTo(centerX + arrowWidthPx, bottom);
            path.lineTo(centerX, myRect.bottom);
            path.lineTo(centerX - arrowWidthPx, bottom);
        }
        path.lineTo(left + bottomLeftDiameter / 2, bottom);

        path.quadTo(left, bottom, left, bottom - bottomLeftDiameter / 2);
        //LEFT, BOTTOM

        if (position == POSITION_LEFT) {
            path.lineTo(left, bottom-(bottom*(1-positionPer))+ arrowWidthPx);
            path.lineTo(myRect.left, bottom-(bottom*(1-positionPer)));
            path.lineTo(left, bottom-(bottom*(1-positionPer)) - arrowWidthPx);
        }
        path.lineTo(left, top + topLeftDiameter / 2);

        path.quadTo(left, top, left + topLeftDiameter / 2, top);

        path.close();

        return path;
    }

    @IntDef({POSITION_LEFT, POSITION_RIGHT, POSITION_TOP, POSITION_BOTTOM})
    public @interface Position {
    }
}
