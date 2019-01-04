package com.ylw.viewdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
    private int shadowColor;
    private RectF rectF;
    private float radiusTop;
    private float radiusBottom;
    private Paint paint;

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
        radiusTop = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslRadiusTop, radius);
        radiusBottom = mTypedArray.getDimension(R.styleable.PaddingShadowLayout_pslRadiusBottom, radius);
        shadowColor = mTypedArray.getColor(R.styleable.PaddingShadowLayout_pslColor, Color.BLUE);
        mTypedArray.recycle();
        setWillNotDraw(false);
        rectF = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        paint.setShadowLayer(radius, 0, 0, shadowColor);
        paint.setColor(shadowColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        canvas.drawRoundRect(rectF, radiusTop, radiusBottom, paint);
    }
}
