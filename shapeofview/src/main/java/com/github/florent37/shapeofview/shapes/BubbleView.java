package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.manager.ClipPathManager;

public class BubbleView extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;

    @Position
    private int position = POSITION_BOTTOM;

    private int borderRadiusPx;
    private int arrowHeightPx;
    private int arrowWidthPx;

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
            borderRadiusPx = attributes.getDimensionPixelSize(R.styleable.BubbleView_shape_bubble_borderRadius, dpToPx(10));
            position = attributes.getInteger(R.styleable.BubbleView_shape_bubble_arrowPosition, position);
            arrowHeightPx = attributes.getDimensionPixelSize(R.styleable.BubbleView_shape_bubble_arrowHeight, dpToPx(10));
            arrowWidthPx = attributes.getDimensionPixelSize(R.styleable.BubbleView_shape_bubble_arrowWidth, dpToPx(10));
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final RectF myRect = new RectF(0, 0, width, height);
                return drawBubble(myRect, borderRadiusPx, borderRadiusPx, borderRadiusPx, borderRadiusPx);
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(@Position int position) {
        this.position = position;
    }

    public void setBorderRadiusPx(int borderRadiusPx) {
        this.borderRadiusPx = borderRadiusPx;
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

        final float centerX = myRect.centerX();

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
            path.lineTo(right, bottom / 2f - arrowWidthPx);
            path.lineTo(myRect.right, bottom / 2f);
            path.lineTo(right, bottom / 2f + arrowWidthPx);
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
            path.lineTo(left, bottom / 2f + arrowWidthPx);
            path.lineTo(myRect.left, bottom / 2f);
            path.lineTo(left, bottom / 2f - arrowWidthPx);
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
