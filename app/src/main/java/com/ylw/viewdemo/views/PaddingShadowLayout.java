package com.ylw.viewdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ylw.viewdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by 袁立位 on 2019/1/4 14:40.
 */
public class PaddingShadowLayout extends FrameLayout {

    private float radius;
    private float shadowRadius;
    private int shadowColor;
    private RectF rectF;
    private float radiusTop;
    private float radiusBottom;
    private Paint paint;
    private Paint clearPaint;
    private Paint visibleShadowPaint;
    private Paint backgroundColorPaint;

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
        radius = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslRadius, 0);
        shadowRadius = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslShadowRadius, 0);
        radiusTop = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslRadiusTop, radius);
        radiusBottom = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslRadiusBottom, radius);
        shadowColor = mTypedArray.getColor(R.styleable.PaddingShadowLayout_pslColor, Color.BLUE);
        mTypedArray.recycle();
        setWillNotDraw(false);
        rectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        paint.setShadowLayer(shadowRadius, 0, 0, shadowColor);
        paint.setColor(shadowColor);
        paint.setAlpha(255);
        clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        visibleShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        visibleShadowPaint.setColor(shadowColor);
        backgroundColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Drawable background = getBackground();
        int backgroundColor = 0x00ffffff;
        if (background instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) background).getColor();
        }
        setBackgroundDrawable(new ColorDrawable(0x00ffffff));
        backgroundColorPaint.setColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            rectF.set(getPaddingLeft() - shadowRadius, getPaddingTop() - shadowRadius, getWidth() - getPaddingRight() + shadowRadius, getHeight() - getPaddingBottom() + shadowRadius);
            canvas.drawRoundRect(rectF, radiusTop, radiusBottom, visibleShadowPaint);
        }
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        canvas.drawRoundRect(rectF, radiusTop, radiusBottom, paint);
        canvas.drawRoundRect(rectF, radiusTop, radiusBottom, clearPaint);
        canvas.drawRoundRect(rectF, radiusTop, radiusBottom, backgroundColorPaint);
    }
}
