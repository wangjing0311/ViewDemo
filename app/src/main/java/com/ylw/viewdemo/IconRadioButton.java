package com.ylw.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

/**
 * Created by 袁立位 on 2018/12/29 16:33.
 */
public class IconRadioButton extends AppCompatRadioButton {

    private Paint textPaint;

    public IconRadioButton(Context context) {
        this(context, null);
    }

    public IconRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textPaint.setTextSize(getTextSize());
        float width = textPaint.measureText(getText().toString());
        Drawable[] compoundDrawables = getCompoundDrawables();
        Drawable leftDrawable = compoundDrawables[0];
        int left = (int) ((getWidth() - width) / 2) - leftDrawable.getMinimumWidth();
        leftDrawable.setBounds(left, 0, leftDrawable.getMinimumWidth() + left, leftDrawable.getMinimumHeight());
        super.onDraw(canvas);
    }
}
