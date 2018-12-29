package com.ylw.viewdemo;

import android.app.Dialog;
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
import android.view.View;

import java.util.Objects;

import androidx.constraintlayout.widget.ConstraintLayout;


public class GuideDialog {

    Dialog dialog;

    public GuideDialog(int layoutId, int focusId, View... views) {
        View view = views[0];
        dialog = new Dialog(view.getContext(), R.style.GuideDialog);
        dialog.setContentView(layoutId);

        float strokeWidth = dp2px(2);
        float insetInner = -dp2px(10);
        float insetOuter = -dp2px(10);
        float radius = dp2px(9);
        float dashGap = dp2px(6);

        DarkLayerDrawable drawable = new DarkLayerDrawable(strokeWidth, insetInner, insetOuter, radius, dashGap, views);
        Objects.requireNonNull(dialog.getWindow()).getDecorView().setBackgroundDrawable(drawable);
        View focus = dialog.findViewById(focusId);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) focus.getLayoutParams();
        layoutParams.leftMargin = drawable.left;
        layoutParams.topMargin = drawable.top;
    }

    public GuideDialog onClick(int btnId, View.OnClickListener listener) {
        dialog.findViewById(btnId).setOnClickListener((v) -> {
            dialog.dismiss();
            listener.onClick(v);
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public int dp2px(float dpValue) {
        final float scale = dialog.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class DarkLayerDrawable extends Drawable {

        private static final String TAG = "DarkLayerDrawable";
        public int left;
        public int top;
        private RectF rect;
        private RectF rectDash;
        private Paint paint;
        private Paint paintDarkBg;
        private Paint paintDash;
        private float radius;
        private float dashGap;

        public DarkLayerDrawable(float strokeWidth, float insetInner, float insetOuter, float radius, float dashGap, View... views) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintDarkBg = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintDash = new Paint(Paint.ANTI_ALIAS_FLAG);

            this.radius = radius;
            this.dashGap = dashGap;

            paint.setColor(0x66ff0000);
            paintDarkBg.setColor(0x66000000);
            paintDash.setColor(0xffffffff);
            paintDash.setStrokeWidth(strokeWidth);
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
            rect.inset(insetInner, insetInner);
            rectDash = new RectF(rect);
            rectDash.inset(insetOuter, insetOuter);
            left = (int) rect.centerX();
            top = (int) rect.bottom;
        }

        @Override
        public void draw(@androidx.annotation.NonNull Canvas canvas) {
            canvas.drawColor(0xaa000000);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRoundRect(rect, radius, radius, paint);
            paint.setXfermode(null);
            Path path = new Path();
            path.addRoundRect(rectDash, radius, radius, Path.Direction.CW);
            paintDash.setPathEffect(new DashPathEffect(new float[]{dashGap, dashGap}, 4));
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

}

