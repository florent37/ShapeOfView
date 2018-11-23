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

public class DiagonalView extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;

    @IntDef(value = {POSITION_BOTTOM, POSITION_TOP, POSITION_LEFT, POSITION_RIGHT})
    public @interface DiagonalPosition{};

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = 2;
    @IntDef(value = {DIRECTION_LEFT, DIRECTION_RIGHT})
    public @interface DiagonalDirection{};

    @DiagonalPosition
    private int diagonalPosition = POSITION_TOP;
    private float diagonalAngle = 0f;

    public DiagonalView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public DiagonalView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DiagonalView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DiagonalView);
            diagonalAngle = attributes.getFloat(R.styleable.DiagonalView_shape_diagonal_angle, diagonalAngle);
            diagonalPosition = attributes.getInteger(R.styleable.DiagonalView_shape_diagonal_position, diagonalPosition);
            attributes.recycle();
        }
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                final float diagonalAngleAbs = Math.abs(diagonalAngle);
                final boolean isDirectionLeft = getDiagonalDirection() == DIRECTION_LEFT;
                final float perpendicularHeight = (float) (width * Math.tan(Math.toRadians(diagonalAngleAbs)));

                switch (diagonalPosition) {
                    case POSITION_BOTTOM:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft(), getPaddingRight());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - perpendicularHeight - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - perpendicularHeight - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.close();
                        }
                        break;
                    case POSITION_TOP:
                        if (isDirectionLeft) {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(width - getPaddingRight(), getPaddingTop() + perpendicularHeight);
                            path.lineTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(getPaddingLeft(), getPaddingTop() + perpendicularHeight);
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        }
                        break;
                    case POSITION_RIGHT:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight() - perpendicularHeight, height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight() - perpendicularHeight, getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        }
                        break;
                    case POSITION_LEFT:
                        if (isDirectionLeft) {
                            path.moveTo(getPaddingLeft() + perpendicularHeight, getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft(), height - getPaddingBottom());
                            path.close();
                        } else {
                            path.moveTo(getPaddingLeft(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), getPaddingTop());
                            path.lineTo(width - getPaddingRight(), height - getPaddingBottom());
                            path.lineTo(getPaddingLeft() + perpendicularHeight, height - getPaddingBottom());
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

    public void setDiagonalPosition(@DiagonalPosition int diagonalPosition) {
        this.diagonalPosition = diagonalPosition;
        requiresShapeUpdate();
    }

    public int getDiagonalPosition() {
        return diagonalPosition;
    }

    @DiagonalDirection
    public int getDiagonalDirection() {
        return diagonalAngle > 0 ? DIRECTION_LEFT : DIRECTION_RIGHT;
    }

    public float getDiagonalAngle() {
        return diagonalAngle;
    }

    public void setDiagonalAngle(float diagonalAngle) {
        this.diagonalAngle = diagonalAngle;
        requiresShapeUpdate();
    }
}
