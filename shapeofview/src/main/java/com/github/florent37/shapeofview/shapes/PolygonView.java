package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.R;
import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.manager.ClipPathManager;

/**
 * Created by Rajat Kumar Gupta on 25-02-2018.
 */

public class PolygonView extends ShapeOfView {
    private int numberOfSides = 4;

    public PolygonView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public PolygonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PolygonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.PolygonView);
            final int sides = attributes.getInteger(R.styleable.PolygonView_shape_polygon_noOfSides, numberOfSides);

            numberOfSides = sides > 3 ? sides : numberOfSides;
            attributes.recycle();
        }

        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {

                final float section = (float) (2.0 * Math.PI / numberOfSides);
                final int polygonSize = Math.min(width, height);
                final int radius = polygonSize / 2;
                final int centerX = width / 2;
                final int centerY = height / 2;

                final Path polygonPath = new Path();
                polygonPath.moveTo((centerX + radius * (float) Math.cos(0)), (centerY + radius * (float) Math.sin(0)));

                for (int i = 1; i < numberOfSides; i++) {
                    polygonPath.lineTo((centerX + radius * (float) Math.cos(section * i)),
                            (centerY + radius * (float) Math.sin(section * i)));
                }

                polygonPath.close();
                return polygonPath;
            }

            @Override
            public boolean requiresBitmap() {
                return true;
            }
        });
    }

    public int getNoOfSides() {
        return numberOfSides;
    }

    public void setNoOfSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
        requiresShapeUpdate();
    }
}
