package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.manager.ClipPathManager;

public class ArcView extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;

    public static final int CROP_INSIDE = 1;
    public static final int CROP_OUTSIDE = 2;

    @ArcPosition
    private int arcPosition = POSITION_TOP;


    private float arcHeightPx = 0f;

    public ArcView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ArcView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ArcView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
            arcHeightPx = attributes.getDimensionPixelSize(R.styleable.ArcView_shape_arc_height, (int) arcHeightPx);
            arcPosition = attributes.getInteger(R.styleable.ArcView_shape_arc_position, arcPosition);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                final boolean isCropInside = getCropDirection() == CROP_INSIDE;

                final float arcHeightAbs = Math.abs(arcHeightPx);

                switch (arcPosition) {
                    case POSITION_BOTTOM: {
                        if (isCropInside) {
                            path.moveTo(0, 0);
                            path.lineTo(0, height);
                            path.quadTo(width / 2, height - 2 * arcHeightAbs, width, height);
                            path.lineTo(width, 0);
                            path.close();
                        } else {
                            path.moveTo(0, 0);
                            path.lineTo(0, height - arcHeightAbs);
                            path.quadTo(width / 2, height + arcHeightAbs, width, height - arcHeightAbs);
                            path.lineTo(width, 0);
                            path.close();
                        }
                        break;
                    }
                    case POSITION_TOP:
                        if (isCropInside) {
                            path.moveTo(0, height);
                            path.lineTo(0, 0);
                            path.quadTo(width / 2, 2 * arcHeightAbs, width, 0);
                            path.lineTo(width, height);
                            path.close();
                        } else {
                            path.moveTo(0, arcHeightAbs);
                            path.quadTo(width / 2, -arcHeightAbs, width, arcHeightAbs);
                            path.lineTo(width, height);
                            path.lineTo(0, height);
                            path.close();
                        }
                        break;
                    case POSITION_LEFT:
                        if (isCropInside) {
                            path.moveTo(width, 0);
                            path.lineTo(0, 0);
                            path.quadTo(arcHeightAbs * 2, height / 2, 0, height);
                            path.lineTo(width, height);
                            path.close();
                        } else {
                            path.moveTo(width, 0);
                            path.lineTo(arcHeightAbs, 0);
                            path.quadTo(-arcHeightAbs, height / 2, arcHeightAbs, height);
                            path.lineTo(width, height);
                            path.close();
                        }
                        break;
                    case POSITION_RIGHT:
                        if (isCropInside) {
                            path.moveTo(0, 0);
                            path.lineTo(width, 0);
                            path.quadTo(width - arcHeightAbs * 2, height / 2, width, height);
                            path.lineTo(0, height);
                            path.close();
                        } else {
                            path.moveTo(0, 0);
                            path.lineTo(width - arcHeightAbs, 0);
                            path.quadTo(width + arcHeightAbs, height / 2, width - arcHeightAbs, height);
                            path.lineTo(0, height);
                            path.close();
                        }
                        break;

                }
                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }


    public int getArcPosition() {
        return arcPosition;
    }

    public void setArcPosition(@ArcPosition int arcPosition) {
        this.arcPosition = arcPosition;
        requiresShapeUpdate();
    }

    public int getCropDirection() {
        return arcHeightPx > 0 ? CROP_OUTSIDE : CROP_INSIDE ;
    }

    public float getArcHeight() {
        return arcHeightPx;
    }

    public void setArcHeight(float arcHeight) {
        this.arcHeightPx = arcHeight;
        requiresShapeUpdate();
    }

    public float getArcHeightDp() {
        return pxToDp(arcHeightPx);
    }

    public void setArcHeightDp(float arcHeight) {
        this.setArcHeight(dpToPx(arcHeight));
    }

    @IntDef(value = {POSITION_BOTTOM, POSITION_TOP, POSITION_LEFT, POSITION_RIGHT})
    public @interface ArcPosition {
    }

    @IntDef(value = {CROP_INSIDE, CROP_OUTSIDE})
    public @interface CropDirection {
    }
}
