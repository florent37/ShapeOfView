package com.github.florent37.shapeofview.manager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ClipDrawableManager implements ClipManager {

    protected Drawable drawable = null;

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @NonNull
    @Override
    public Bitmap createMask(int width, int height) {
        final Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mask);
        if (drawable != null) {
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
        }
        return mask;
    }

    @Nullable
    @Override
    public Path getShadowConvexPath() {
        return null;
    }

    @Override
    public void setupClipLayout(int width, int height) {

    }
}
