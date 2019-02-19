package com.ylw.viewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间段选择器
 * <p>
 * Created by 袁立位 on 2019/2/19 14:06.
 */
public class BottomSheetDialogTimeRangePicker {

    private static final String TAG = "DialogTimeRangePicker";
    private Activity context;
    private TextView curSelect;
    private Date startDate = new Date(curMillis());
    private Date endDate = new Date(curMillis());
    private int dayOfMonth = 30;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public BottomSheetDialogTimeRangePicker(MainMenuActivity activity) {
        this.context = activity;
    }

    public BottomSheetDialogTimeRangePicker setStartDate(Date date) {
        startDate = date;
        return this;
    }

    public BottomSheetDialogTimeRangePicker setEndDate(Date date) {
        endDate = date;
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void show(IOnSelectDateRange onSelectDateTime) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        View dialogView = context.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_picker_style_3, null);
        WheelView wheelView1 = dialogView.findViewById(R.id.wheel1);
        WheelView wheelView2 = dialogView.findViewById(R.id.wheel2);
        WheelView wheelView3 = dialogView.findViewById(R.id.wheel3);
        Button cancel = dialogView.findViewById(R.id.cancel);
        Button ok = dialogView.findViewById(R.id.ok);
        TextView startTime = dialogView.findViewById(R.id.start_time);
        TextView endTime = dialogView.findViewById(R.id.end_time);
        startTime.setText(format.format(startDate));
        endTime.setText(format.format(endDate));
        curSelect = startTime;
        startTime.setOnClickListener(v -> {
            curSelect = startTime;
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(format.parse(curSelect.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            wheelView1.setCurrentItem(calendar.get(Calendar.YEAR) - Calendar.getInstance().get(Calendar.YEAR));
            wheelView2.setCurrentItem(calendar.get(Calendar.MONTH));
            wheelView3.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        });
        endTime.setOnClickListener(v -> {
            curSelect = endTime;
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(format.parse(curSelect.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            wheelView1.setCurrentItem(calendar.get(Calendar.YEAR) - Calendar.getInstance().get(Calendar.YEAR));
            wheelView2.setCurrentItem(calendar.get(Calendar.MONTH));
            wheelView3.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        });
        cancel.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        ok.setOnClickListener(v -> {
            Date start = null;
            Date end = null;
            try {
                start = format.parse(startTime.getText().toString());
                end = format.parse(endTime.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mBottomSheetDialog.dismiss();
            onSelectDateTime.onResult(start, end);
        });
        View.OnTouchListener touchHandler = (v, e) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            v.performClick();
            return false;
        };
        wheelView1.setOnTouchListener(touchHandler);
        wheelView2.setOnTouchListener(touchHandler);
        wheelView3.setOnTouchListener(touchHandler);
        OnItemSelectedListener onSelectedListener = index -> {
            initViews(wheelView1, wheelView2, wheelView3);
        };
        wheelView1.setOnItemSelectedListener(onSelectedListener);
        wheelView2.setOnItemSelectedListener(onSelectedListener);
        wheelView3.setOnItemSelectedListener(onSelectedListener);
        WheelAdapter adapter1 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return 10;
            }

            @Override
            public Object getItem(int index) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(curMillis()));
                calendar.add(Calendar.YEAR, index);
                if (index == wheelView1.getCurrentItem()) {
                    initViews(wheelView1, wheelView2, wheelView3);
                    return "  " + calendar.get(Calendar.YEAR) + "年";
                }
                return "  " + calendar.get(Calendar.YEAR);
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
                    initViews(wheelView1, wheelView2, wheelView3);
                    return String.format(Locale.getDefault(), "  %02d月", index + 1);
                }
                return String.format(Locale.getDefault(), "  %02d", index + 1);
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        WheelAdapter adapter3 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return dayOfMonth;
            }

            @Override
            public Object getItem(int index) {
                if (index == wheelView3.getCurrentItem()) {
                    initViews(wheelView1, wheelView2, wheelView3);
                    return String.format(Locale.getDefault(), "  %02d日", index + 1);
                }
                return String.format(Locale.getDefault(), "  %02d", index + 1);
            }

            @Override
            public int indexOf(Object o) {
                Log.i(TAG, "indexOf: " + o);
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
        calendar.setTime(startDate);
        wheelView2.setCurrentItem(calendar.get(Calendar.MONTH));
        wheelView3.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH));

        mBottomSheetDialog.setContentView(dialogView);
        mBottomSheetDialog.show();
    }

    private void initViews(WheelView wheelView1, WheelView wheelView2, WheelView wheelView3) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(curMillis()));
        calendar.set(calendar.get(Calendar.YEAR) + wheelView1.getCurrentItem(), wheelView2.getCurrentItem() + 1, 0);
        if (wheelView3.getCurrentItem() > calendar.get(Calendar.DAY_OF_MONTH) - 1) {
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            wheelView3.setCurrentItem(dayOfMonth - 1);
        }
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        Log.i(TAG, "initViews: dayOfMonth:" + dayOfMonth);
        wheelView3.postInvalidate();
        calendar.set(Calendar.DAY_OF_MONTH, wheelView3.getCurrentItem() + 1);
        curSelect.setText(format.format(calendar.getTime()));
    }

    private long curMillis() {
        return System.currentTimeMillis();
    }

    interface IOnSelectDateRange {
        void onResult(Date start, Date end);
    }
}
