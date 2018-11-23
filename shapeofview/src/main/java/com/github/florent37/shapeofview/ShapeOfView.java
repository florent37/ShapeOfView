package com.github.florent37.shapeofview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.appcompat.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import com.github.florent37.shapeofview.manager.ClipManager;
import com.github.florent37.shapeofview.manager.ClipPathManager;

public class ShapeOfView extends FrameLayout {

    private final Paint clipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path clipPath = new Path();

    protected PorterDuffXfermode pdMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    @Nullable
    protected Drawable drawable = null;
    private ClipManager clipManager = new ClipPathManager();
    private boolean requiersShapeUpdate = true;
    private Bitmap clipBitmap;

    final Path rectView = new Path();

    public ShapeOfView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ShapeOfView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShapeOfView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public void setBackground(Drawable background) {
        //disabled here, please set a background to to this view child
        //super.setBackground(background);
    }

    @Override
    public void setBackgroundResource(int resid) {
        //disabled here, please set a background to to this view child
        //super.setBackgroundResource(resid);
    }

    @Override
    public void setBackgroundColor(int color) {
        //disabled here, please set a background to to this view child
        //super.setBackgroundColor(color);
    }

    private void init(Context context, AttributeSet attrs) {
        clipPaint.setAntiAlias(true);

        setDrawingCacheEnabled(true);

        setWillNotDraw(false);

        clipPaint.setColor(Color.BLUE);
        clipPaint.setStyle(Paint.Style.FILL);
        clipPaint.setStrokeWidth(1);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1){
            clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            setLayerType(LAYER_TYPE_SOFTWARE, clipPaint); //Only works for software layers
        } else {
            clipPaint.setXfermode(pdMode);
            setLayerType(LAYER_TYPE_SOFTWARE, null); //Only works for software layers
        }

        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ShapeOfView);

            if (attributes.hasValue(R.styleable.ShapeOfView_shape_clip_drawable)) {
                final int resourceId = attributes.getResourceId(R.styleable.ShapeOfView_shape_clip_drawable, -1);
                if (-1 != resourceId) {
                    setDrawable(resourceId);
                }
            }

            attributes.recycle();
        }
    }

    protected float dpToPx(float dp) {
        return dp * this.getContext().getResources().getDisplayMetrics().density;
    }

    protected float pxToDp(float px) {
        return px / this.getContext().getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            requiresShapeUpdate();
        }
    }

    private boolean requiresBitmap() {
        return isInEditMode() || (clipManager != null && clipManager.requiresBitmap()) || drawable != null;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        requiresShapeUpdate();
    }

    public void setDrawable(int redId) {
        setDrawable(AppCompatResources.getDrawable(getContext(), redId));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (requiersShapeUpdate) {
            calculateLayout(canvas.getWidth(), canvas.getHeight());
            requiersShapeUpdate = false;
        }
        if (requiresBitmap()) {
            clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(clipBitmap, 0, 0, clipPaint);
        } else {
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1){
                canvas.drawPath(clipPath, clipPaint);
            } else {
                canvas.drawPath(rectView, clipPaint);
            }
        }

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
    }

    private void calculateLayout(int width, int height) {
        rectView.reset();
        rectView.addRect(0f, 0f, 1f * getWidth(), 1f * getHeight(), Path.Direction.CW);

        if (clipManager != null) {
            if (width > 0 && height > 0) {
                clipManager.setupClipLayout(width, height);
                clipPath.reset();
                clipPath.set(clipManager.createMask(width, height));

                if (requiresBitmap()) {
                    if (clipBitmap != null) {
                        clipBitmap.recycle();
                    }
                    clipBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    final Canvas canvas = new Canvas(clipBitmap);

                    if (drawable != null) {
                        drawable.setBounds(0, 0, width, height);
                        drawable.draw(canvas);
                    } else {
                        canvas.drawPath(clipPath, clipManager.getPaint());
                    }
                }

                //invert the path for android P
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                    final boolean success = rectView.op(clipPath, Path.Op.DIFFERENCE);
                }

                //this needs to be fixed for 25.4.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && ViewCompat.getElevation(this) > 0f) {
                    try {
                        setOutlineProvider(getOutlineProvider());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        postInvalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ViewOutlineProvider getOutlineProvider() {
        return new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                if (clipManager != null && !isInEditMode()) {
                    final Path shadowConvexPath = clipManager.getShadowConvexPath();
                    if (shadowConvexPath != null) {
                        try {
                            outline.setConvexPath(shadowConvexPath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };
    }

    public void setClipPathCreator(ClipPathManager.ClipPathCreator createClipPath) {
        ((ClipPathManager) clipManager).setClipPathCreator(createClipPath);
        requiresShapeUpdate();
    }

    public void requiresShapeUpdate() {
        this.requiersShapeUpdate = true;
        postInvalidate();
    }

}
