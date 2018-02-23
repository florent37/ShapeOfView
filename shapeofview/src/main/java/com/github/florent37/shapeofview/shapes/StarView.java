package com.github.florent37.shapeofview.shapes;

import android.content.Context;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.shapeofview.ShapeOfView;
import com.github.florent37.shapeofview.manager.ClipPathManager;

/**
 * Created by Rajat Kumar Gupta on 23-02-2018.
 */

public class StarView extends ShapeOfView {

    public StarView(@NonNull Context context) {
        super(context);
        init(context, null);

    }

    public StarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs) {

        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                Path path = new Path();

                float mid = width / 2;
                float min = Math.min(width, height);
                float half = min/2 ;
                mid = mid - half;

                // top left
                path.moveTo(mid + half * 0.5f, half * 0.84f);
                // top right
                path.lineTo(mid + half * 1.5f, half * 0.84f);
                // bottom left
                path.lineTo(mid + half * 0.68f, half * 1.45f);
                // top tip
                path.lineTo(mid + half * 1.0f, half * 0.5f);
                // bottom right
                path.lineTo(mid + half * 1.32f, half * 1.45f);
                // top left
                path.lineTo(mid + half * 0.5f, half * 0.84f);

                return path;
            }
        });
        postInvalidate();
    }

}
