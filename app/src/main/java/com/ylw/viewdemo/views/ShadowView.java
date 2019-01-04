package com.ylw.viewdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ylw.viewdemo.R;

import androidx.annotation.Nullable;

/**
 * Created by 袁立位 on 2019/1/4 10:20.
 */
public class ShadowView extends View {

    private int type;
    private int startColor = Color.BLUE;
    private int endColor = Color.GRAY;
    private Paint paint;
    private float stopPosition = 0.5f;

    public ShadowView(Context context) {
        super(context);
        init(context, null);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowView);
            type = mTypedArray.getInteger(R.styleable.ShadowView_svType, 0);
            startColor = mTypedArray.getColor(R.styleable.ShadowView_svStartColor, Color.BLUE);
            endColor = mTypedArray.getColor(R.styleable.ShadowView_svEndColor, Color.GRAY);
            stopPosition = mTypedArray.getFloat(R.styleable.ShadowView_svStopPosition, 0.5f);
            mTypedArray.recycle();
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        switch (type) {
            case 0:  // left
                paint.setShader(new LinearGradient(getWidth(), 0, 0, 0, colors(), positions(), Shader.TileMode.CLAMP));
                canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
                break;
            case 1:  // top
                paint.setShader(new LinearGradient(0, getHeight(), 0, 0, colors(), positions(), Shader.TileMode.CLAMP));
                canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
                break;
            case 2:  // right
                paint.setShader(new LinearGradient(0, 0, getWidth(), 0, colors(), positions(), Shader.TileMode.CLAMP));
                canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
                break;
            case 3:  // bottom
//                paint.setShader(new LinearGradient(0, 0, 0, getHeight(), colors(), positions(), Shader.TileMode.CLAMP));
//                canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setShadowLayer(30, 0, 0, 0x22103EE8);
                canvas.drawRoundRect(new RectF(0+30, 0+30, getWidth()-60, getHeight()-30), 30,30, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                canvas.drawRect(new Rect(0+80, 0+80, getWidth()-160, getHeight()-130), paint);
                paint.setXfermode(null);
                break;
            case 4:  // leftTop
                break;
            case 5:  // rightTop
                break;
            case 6:  // leftBottom
                break;
            case 7:  // rightBottom
                break;
        }
    }

    int[] colors() {
        int startColor = this.startColor;
        int endColor = this.endColor;
        int[] ints = new int[11];
        int startAlpha = Color.alpha(startColor);
        int startRed = Color.red(startColor);
        int startGreen = Color.green(startColor);
        int startBlue = Color.blue(startColor);
        int endAlpha = Color.alpha(endColor);
        int endRed = Color.red(endColor);
        int endGreen = Color.green(endColor);
        int endBlue = Color.blue(endColor);
        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        for (int i = 0; i < ints.length; i++) {
            float interpolation = interpolator.getInterpolation(i * 1f / (ints.length - 1));
            interpolation = (float) Math.sqrt(interpolation);
            ints[i] = Color.argb(
                    (int) (startAlpha + (endAlpha - startAlpha) * interpolation),
                    (int) (startRed + (endRed - startRed) * interpolation),
                    (int) (startGreen + (endGreen - startGreen) * interpolation),
                    (int) (startBlue + (endBlue - startBlue) * interpolation)
            );
        }
        return ints;
    }

    float[] positions() {
        return new float[]{0, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1};
    }
}
