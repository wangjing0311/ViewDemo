package com.ylw.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;

import androidx.annotation.Nullable;

/**
 * Created by 袁立位 on 2019/1/17 10:47.
 */
public class TouchView extends View {

    float x;
    float y;
    private Paint paint;
    private RectF oval;

    public TouchView(Context context) {
        super(context);
        init();
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        oval = new RectF(0, 0, 30, 30);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        oval.offsetTo(x, y);
        oval.offset(-oval.width() / 2, -oval.height() / 2);
        paint.setColor(Color.BLUE);
        canvas.drawArc(oval, 0, 360, true, paint);
        canvas.drawText(String.format(Locale.getDefault(), "x:%.04f y:%.04f",
                Math.min(getWidth(), Math.max(0, x)) / getWidth(),
                Math.min(getHeight(), Math.max(0, y)) / getHeight()), 10, 30, paint);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        postInvalidate();
        return super.dispatchTouchEvent(event);
    }


}
