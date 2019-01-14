package com.ylw.viewdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.ylw.viewdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 带有圆角阴影的Layout
 * <p>
 * 这个layout可以在四周添加 padding，阴影会被绘制在 padding 出来的空白区域内，没有添加 padding 的边不会绘制阴影
 * <p>
 * pslCornerRadius : 属性控制内容区域的圆角大小
 * pslShadowRadius : 属性控制阴影的范围
 * pslShadowColor  : 属性控制阴影的颜色
 *
 *
 * <p>
 * Created by 袁立位 on 2019/1/4 14:40.
 */
public class PaddingShadowLayout extends FrameLayout {

    private static final String TAG = "PaddingShadowLayout";
    private float shadowRadius;
    private RectF rectF;
    private float cornerRadius;
    private Paint paintLine;
    private Paint paintCorner;
    private Paint backgroundColorPaint;
    private int shadowColor;
    private LinearGradient shaderLine;
    private RadialGradient shaderCorner;

    public PaddingShadowLayout(@NonNull Context context) {
        super(context);
    }

    public PaddingShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PaddingShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.PaddingShadowLayout);
        cornerRadius = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslCornerRadius, 0);
        shadowRadius = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslShadowRadius, 0);
        shadowColor = mTypedArray.getColor(R.styleable.PaddingShadowLayout_pslShadowColor, Color.BLUE);
        mTypedArray.recycle();
        setWillNotDraw(false);
        rectF = new RectF();

        paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLine.setStyle(Paint.Style.FILL);

        paintCorner = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCorner.setStyle(Paint.Style.STROKE);

        backgroundColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Drawable background = getBackground();
        int backgroundColor = 0x00ffffff;
        if (background instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) background).getColor();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(new ColorDrawable(0x00ffffff));
        }
        backgroundColorPaint.setColor(backgroundColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        shaderLine = null;
        shaderCorner = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
//        visibleShadowPaint.setColor(0x220000ff);
//        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, visibleShadowPaint);

        float w = getWidth();
        float h = getHeight();
        float l = 0;
        float t = 0;
        float r = 0;
        float b = 0;
        Log.i(TAG, "onDraw: w:" + w + " h:" + h
                + " pl:" + getPaddingLeft()
                + " pt:" + getPaddingTop()
                + " pr:" + getPaddingRight()
                + " pb:" + getPaddingBottom()
                + " shadowRadius:" + shadowRadius
                + " cornerRadius:" + cornerRadius);
        if (getPaddingLeft() > 0) {
            l = getPaddingLeft() - shadowRadius;
            t = getPaddingTop() == 0 ? 0 : cornerRadius + getPaddingTop();
            r = getPaddingLeft();
            b = getPaddingBottom() == 0 ? h : h - cornerRadius - getPaddingBottom();

            float x = getPaddingLeft();
            float y = t;
            float length = b - t;
            float degrees = 90;
            drawShadowLine(canvas, x, y, length, degrees);
        }
        if (getPaddingTop() > 0) {
            l = getPaddingLeft() == 0 ? 0 : cornerRadius + getPaddingLeft();
            t = getPaddingTop() - shadowRadius;
            r = getPaddingRight() == 0 ? w : w - cornerRadius - getPaddingRight();
            b = getPaddingTop();

            float x = l + r - l;
            float y = b;
            float length = r - l;
            float degrees = 180;
            drawShadowLine(canvas, x, y, length, degrees);
        }
        if (getPaddingRight() > 0) {
            l = w - getPaddingRight();
            t = getPaddingTop() == 0 ? 0 : cornerRadius + getPaddingTop();
            r = l + shadowRadius;
            b = getPaddingBottom() == 0 ? h : h - cornerRadius - getPaddingBottom();

            float x = l;
            float y = t + b - t;
            float length = b - t;
            float degrees = 270;
            drawShadowLine(canvas, x, y, length, degrees);
        }
        if (getPaddingBottom() > 0) {
            l = getPaddingLeft() == 0 ? 0 : cornerRadius + getPaddingLeft();
            t = h - getPaddingBottom();
            r = getPaddingRight() == 0 ? w : w - cornerRadius - getPaddingRight();
            b = t + shadowRadius;
            float x = l;
            float y = t;
            float length = r - l;
            float degrees = 0;
            drawShadowLine(canvas, x, y, length, degrees);
        }

        if (getPaddingLeft() > 0 && getPaddingTop() > 0) {
            float x = getPaddingLeft() + cornerRadius;
            float y = getPaddingTop() + cornerRadius;
            drawCorner(canvas, x, y, 0);
        }

        if (getPaddingRight() > 0 && getPaddingTop() > 0) {
            float x = w - getPaddingRight() - cornerRadius;
            float y = getPaddingTop() + cornerRadius;
            drawCorner(canvas, x, y, 90);
        }

        if (getPaddingRight() > 0 && getPaddingBottom() > 0) {
            float x = w - getPaddingRight() - cornerRadius;
            float y = h - getPaddingBottom() - cornerRadius;
            drawCorner(canvas, x, y, 180);
        }

        if (getPaddingLeft() > 0 && getPaddingBottom() > 0) {
            float x = getPaddingLeft() + cornerRadius;
            float y = h - getPaddingBottom() - cornerRadius;
            drawCorner(canvas, x, y, 270);
        }

        rectF.set(getPaddingLeft() == 0 ? -100 : getPaddingLeft(),
                getPaddingTop() == 0 ? -100 : getPaddingTop(),
                getPaddingRight() == 0 ? (getWidth() + 100) : (getWidth() - getPaddingRight()),
                getPaddingBottom() == 0 ? (getHeight() + 100) : (getHeight() - getPaddingBottom()));
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, backgroundColorPaint);
    }

    private void drawShadowLine(Canvas canvas, float x, float y, float length, float degrees) {
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(degrees);
        rectF.set(0, 0, length, shadowRadius);// ↓↓↓↓↓↓↓↓↓
        if (shaderLine == null) {
            shaderLine = new LinearGradient(0, 0, 0, shadowRadius, colors(), positions(), Shader.TileMode.CLAMP);
        }
        paintLine.setShader(shaderLine);
        paintLine.setStyle(Paint.Style.FILL);
        canvas.drawRect(rectF, paintLine);
        canvas.restore();
    }

    private void drawCorner(Canvas canvas, float x, float y, int degrees) {
        float l;
        float t;
        float r;
        float b;
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(degrees);
        l = -shadowRadius - cornerRadius + shadowRadius / 2;
        t = -shadowRadius - cornerRadius + shadowRadius / 2;
        r = shadowRadius + cornerRadius - shadowRadius / 2;
        b = shadowRadius + cornerRadius - shadowRadius / 2;
        rectF.set(l, t, r, b);
        paintCorner.setStrokeWidth(shadowRadius);
        if (shaderCorner == null) {
            shaderCorner = new RadialGradient(-1, -1, shadowRadius + cornerRadius,
                    colorsR(), positionsR(), Shader.TileMode.CLAMP);
        }
        paintCorner.setShader(shaderCorner);
        Path mCornerShadowPath = new Path();
        mCornerShadowPath.reset();
        mCornerShadowPath.moveTo(l, b);
        mCornerShadowPath.arcTo(rectF, 180, 90f, true);
        canvas.drawPath(mCornerShadowPath, paintCorner);

//        rectF.set(l, t, r, b);
//        paint.setColor(0x9900ff00);
//        paint.setShader(null);
//        paint.setStrokeWidth(1);
//        canvas.drawRect(rectF, paint);
        canvas.restore();
    }

    int[] colors() {
        int startColor = shadowColor;
        int[] ints = new int[11];
        int startAlpha = Color.alpha(startColor);
        int r = Color.red(startColor);
        int g = Color.green(startColor);
        int b = Color.blue(startColor);
        int endAlpha = 0;
        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        for (int i = 0; i < ints.length; i++) {
            float interpolation = interpolator.getInterpolation(i * 1f / (ints.length - 1));
            interpolation = (float) Math.sqrt(interpolation);
            ints[i] = Color.argb(
                    (int) (startAlpha + (endAlpha - startAlpha) * interpolation),
                    r, g, b);
        }
        return ints;
    }


    float[] positions() {
        return new float[]{0, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1};
    }


    int[] colorsR() {
        int startColor = shadowColor;
        int[] ints = new int[12];
        int startAlpha = Color.alpha(startColor);
        int r = Color.red(startColor);
        int g = Color.green(startColor);
        int b = Color.blue(startColor);
        int endAlpha = 0;
        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        for (int i = 1; i < ints.length; i++) {
            float interpolation = interpolator.getInterpolation((i - 1) * 1f / (ints.length - 2));
            interpolation = (float) Math.sqrt(interpolation);
            ints[i] = Color.argb(
                    (int) (startAlpha + (endAlpha - startAlpha) * interpolation),
                    r, g, b);
        }
        ints[0] = ints[1] = Color.argb(startAlpha, r, g, b);
        return ints;
    }

    float[] positionsR() {
        float r = shadowRadius + cornerRadius;
        float start = cornerRadius / r;
        float[] poss = new float[12];
        poss[1] = start;
        for (int i = 0; i < 11; i++) {
            poss[i + 1] = (cornerRadius + shadowRadius * i / 10) / r;
        }
        return poss;
    }
}
