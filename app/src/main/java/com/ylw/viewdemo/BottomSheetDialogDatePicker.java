package com.ylw.viewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期选择器
 * <p>
 * 选择器样式：<br/>
 * <img src="http://192.168.0.34/AndroidGroup/up366TeacherV4/uploads/ee11b37075182398c625a3bec7aac615/image.png"/>
 * <p>
 * Created by 袁立位 on 2019/2/20 14:18.
 */
public class BottomSheetDialogDatePicker {

    private Activity context;
    private Date startDate = new Date(curMillis());

    public BottomSheetDialogDatePicker(Activity activity) {
        this.context = activity;
    }

    public BottomSheetDialogDatePicker setStartDate(Date date) {
        startDate = date;
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void show(IOnSelectDate onSelectDate) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        View dialogView = context.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_picker_style_4, null);
        WheelView wheelView1 = dialogView.findViewById(R.id.wheel1);
        WheelView wheelView2 = dialogView.findViewById(R.id.wheel2);
        Button cancel = dialogView.findViewById(R.id.cancel);
        Button ok = dialogView.findViewById(R.id.ok);
        cancel.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        ok.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, wheelView1.getCurrentItem());
            calendar.set(Calendar.MONTH, wheelView2.getCurrentItem());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            mBottomSheetDialog.dismiss();
            onSelectDate.onResult(calendar.getTime());
        });
        View.OnTouchListener touchHandler = (v, e) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            v.performClick();
            return false;
        };
        wheelView1.setOnTouchListener(touchHandler);
        wheelView2.setOnTouchListener(touchHandler);

        WheelAdapter adapter1 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return 10;
            }

            @Override
            public Object getItem(int index) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.YEAR, index);
                if (index == wheelView1.getCurrentItem()) {
                    return calendar.get(Calendar.YEAR) + "年";
                }
                return calendar.get(Calendar.YEAR);
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        WheelAdapter adapter2 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return 12;
            }

            @Override
            public Object getItem(int index) {
                if (index == wheelView2.getCurrentItem()) {
                    return String.format(Locale.getDefault(), "  %02d月", index + 1);
                }
                return String.format(Locale.getDefault(), "  %02d", index + 1);
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        wheelView1.setCyclic(false);
        wheelView1.setGravity(Gravity.CENTER);
        wheelView2.setGravity(Gravity.LEFT);
        wheelView1.setAdapter(adapter1);
        wheelView2.setAdapter(adapter2);
        Calendar calendar = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        calendar.setTime(new Date(curMillis()));
        startCalendar.setTime(startDate);
        wheelView1.setCurrentItem(calendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR));
        wheelView2.setCurrentItem(calendar.get(Calendar.MONTH));

        mBottomSheetDialog.setContentView(dialogView);
        mBottomSheetDialog.show();
    }

    private long curMillis() {
        return System.currentTimeMillis();
    }

    interface IOnSelectDate {
        void onResult(Date date);
    }
}
