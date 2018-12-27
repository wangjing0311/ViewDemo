package com.ylw.viewdemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

public class DarkLayerDrawable extends Drawable {


    private static final String TAG = "DarkLayerDrawable";
    public int left;
    public int top;
    private RectF rect;
    private RectF rectDash;
    private Paint paint;
    private Paint paintDarkBg;
    private Paint paintDash;

    public DarkLayerDrawable(View... views) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDarkBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintDash = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(0x66ff0000);
        paintDarkBg.setColor(0x66000000);
        paintDash.setColor(0xffffffff);
        paintDash.setStrokeWidth(6);
        paintDash.setStyle(Paint.Style.STROKE);

        for (View view : views) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            int width = view.getWidth();
            int height = view.getHeight();
            if (rect == null) {
                rect = new RectF(location[0], location[1], location[0] + width, location[1] + height);
            } else {
                rect.union(location[0], location[1], location[0] + width, location[1] + height);
            }
        }
        rect.inset(-30, -30);
        rectDash = new RectF(rect);
        rectDash.inset(-30, -30);
        left = (int) rect.centerX();
        top = (int) rect.bottom;
    }

    @Override
    public void draw(@androidx.annotation.NonNull Canvas canvas) {
        canvas.drawColor(0xaa000000);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(rect, 30, 30, paint);
        paint.setXfermode(null);
        Path path = new Path();
        path.addRoundRect(rectDash, 30, 30, Path.Direction.CW);
        paintDash.setPathEffect(new DashPathEffect(new float[]{20, 20}, 4));
        canvas.drawPath(path, paintDash);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@androidx.annotation.Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
