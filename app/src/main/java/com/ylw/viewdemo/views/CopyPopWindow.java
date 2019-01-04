package com.ylw.viewdemo.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by 袁立位 on 2019/1/4 19:21.
 */
public class CopyPopWindow {

    private static final String TAG = "CopyPopWindow";

    public void show(TextView view) {
        ArrowTextView vv = new ArrowTextView(view.getContext());
        vv.setText("复制");
        vv.setPadding(dp2px(view.getContext(), 15),
                dp2px(view.getContext(), 10),
                dp2px(view.getContext(), 15),
                dp2px(view.getContext(), 20));
        vv.setTextColor(0xffffffff);
        vv.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        PopupWindow copyPopWindow = new PopupWindow(view, vv.getMeasuredWidth(), vv.getMeasuredHeight(), true);
        copyPopWindow.setContentView(vv);
        ViewGroup.LayoutParams layoutParams = vv.getLayoutParams();
        if (layoutParams == null) {
            vv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        Log.i(TAG, "show: " + layoutParams);
        copyPopWindow.showAsDropDown(view, vv.getMeasuredWidth(), -view.getHeight() - vv.getMeasuredHeight());
        view.setBackgroundColor(0x664F73FB);
        copyPopWindow.setOnDismissListener(() -> {
            view.setBackgroundColor(0x00FFFFFF);
        });
        vv.setOnClickListener(v -> {
            copyPopWindow.dismiss();
            ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", view.getText());
            if (cm != null) {
                cm.setPrimaryClip(clipData);
            }
            Toast.makeText(view.getContext(), "已复制到粘贴板", Toast.LENGTH_SHORT).show();
        });
    }

    class ArrowTextView extends AppCompatTextView {

        private RectF rect;
        private Paint paint;

        public ArrowTextView(Context context) {
            super(context);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(0xff333333);
            rect = new RectF();
        }

        public ArrowTextView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public ArrowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            float arrow = dp2px(getContext(), 20);
            rect.set(0, 0, getWidth(), getHeight() - arrow / 2);
            canvas.drawRoundRect(rect, dp2px(getContext(), 5), dp2px(getContext(), 5), paint);
            canvas.save();
//            canvas.rotate(45);
            canvas.translate(rect.centerX(), rect.height() - arrow);
            canvas.rotate(45, 0, 0);
            rect.set(0, 0, arrow, arrow);
            canvas.drawRect(rect, paint);
            canvas.restore();
            super.onDraw(canvas);
        }
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
