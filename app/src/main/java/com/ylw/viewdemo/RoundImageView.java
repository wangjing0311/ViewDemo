package com.ylw.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 圆角图片
 * <p>
 * Created by 袁立位 on 2019/1/17 15:08.
 */
public class RoundImageView extends AppCompatImageView {

    private RectF rect;
    private Path path;
    private float cornerRadius;
    private Paint paint;

    public RoundImageView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rect = new RectF();
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            cornerRadius = mTypedArray.getDimension(R.styleable.RoundImageView_rivCornerRadius, 0);
            int aliasColor = mTypedArray.getColor(R.styleable.RoundImageView_rivAliasColor, 0x66ffffff);
            paint.setColor(aliasColor);
            mTypedArray.recycle();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        rect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        path.reset();
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path);
        super.draw(canvas);
        canvas.restore();
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
    }
}
