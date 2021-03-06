package com.ylw.viewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间选择器
 * <p>
 * Created by 袁立位 on 2019/2/18 15:50.
 */
public class BottomSheetDialogDateTimePicker {

    private Activity context;

    public BottomSheetDialogDateTimePicker(Activity activity) {
        this.context = activity;
    }

    /**
     * 日期时间选择器
     * <p>
     * 选择器样式：<br/>
     * <img src="http://192.168.0.34/AndroidGroup/up366TeacherV4/uploads/4024024147a41f21855af2c76d4e5330/image.png"/>
     */
    @SuppressLint("ClickableViewAccessibility")
    public void show(IOnSelectDateTime onSelectDateTime) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        View dialogView = context.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_picker_style_1, null);
        WheelView wheelView1 = dialogView.findViewById(R.id.wheel1);
        WheelView wheelView2 = dialogView.findViewById(R.id.wheel2);
        WheelView wheelView3 = dialogView.findViewById(R.id.wheel3);
        Button cancel = dialogView.findViewById(R.id.cancel);
        Button ok = dialogView.findViewById(R.id.ok);
        TextView year = dialogView.findViewById(R.id.year);
        cancel.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        ok.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_WEEK, wheelView1.getCurrentItem());
            calendar.set(Calendar.HOUR_OF_DAY, wheelView2.getCurrentItem());
            calendar.set(Calendar.MINUTE, wheelView3.getCurrentItem());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            mBottomSheetDialog.dismiss();
            onSelectDateTime.onResult(calendar.getTime());
        });
        View.OnTouchListener touchHandler = (v, e) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            v.performClick();
            return false;
        };
        wheelView1.setOnTouchListener(touchHandler);
        wheelView2.setOnTouchListener(touchHandler);
        wheelView3.setOnTouchListener(touchHandler);
        SimpleDateFormat format1 = new SimpleDateFormat("  MM月dd日", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("  MM.dd", Locale.getDefault());
        WheelAdapter adapter1 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return 1000;
            }

            @Override
            public Object getItem(int index) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_WEEK, index);
                Date d = calendar.getTime();
                if (index == wheelView1.getCurrentItem()) {
                    year.setText(String.valueOf(calendar.get(Calendar.YEAR)));
                    return format1.format(d);
                }
                return format2.format(d);
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        WheelAdapter adapter2 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return 24;
            }

            @Override
            public Object getItem(int index) {
                if (index == wheelView2.getCurrentItem()) {
                    return "  " + String.valueOf(index) + "时";
                }
                return "  " + String.valueOf(index);
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        WheelAdapter adapter3 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return 60;
            }

            @Override
            public Object getItem(int index) {
                if (index == wheelView3.getCurrentItem()) {
                    return "  " + String.valueOf(index) + "分";
                }
                return "  " + String.valueOf(index);
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        wheelView1.setCyclic(false);
        wheelView1.setGravity(Gravity.LEFT);
        wheelView2.setGravity(Gravity.LEFT);
        wheelView3.setGravity(Gravity.LEFT);
        wheelView1.setAdapter(adapter1);
        wheelView2.setAdapter(adapter2);
        wheelView3.setAdapter(adapter3);
        wheelView1.setCurrentItem(0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        wheelView2.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
        wheelView3.setCurrentItem(calendar.get(Calendar.MINUTE));
        mBottomSheetDialog.setContentView(dialogView);
        mBottomSheetDialog.show();
    }

    public interface IOnSelectDateTime {
        void onResult(Date date);
    }
}
