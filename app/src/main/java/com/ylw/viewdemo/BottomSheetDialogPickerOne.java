package com.ylw.viewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用单项选择器
 * <p>
 * Created by 袁立位 on 2019/2/19 13:00.
 */
public class BottomSheetDialogPickerOne<T> {

    private Activity context;
    private List items = new ArrayList<>();
    private IDataRender<T> render = Object::toString;
    private boolean isCyclic = false;

    public BottomSheetDialogPickerOne(MainMenuActivity activity) {
        this.context = activity;
    }

    public BottomSheetDialogPickerOne setData(List items) {
        this.items = items;
        return this;
    }

    /**
     * 设置数据是否可以循环，默认值：false
     */
    public BottomSheetDialogPickerOne setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        return this;
    }

    public BottomSheetDialogPickerOne setRender(IDataRender<T> render) {
        this.render = render;
        return this;
    }


    /**
     * 单项选择器
     * <p>
     * 使用方式
     * <p>
     * 选择器样式：<br/>
     * <img src="http://192.168.0.34/AndroidGroup/up366TeacherV4/uploads/ebb1dd78bd224d1403db94692d57f16b/image.png"/>
     */
    @SuppressLint("ClickableViewAccessibility")
    public void show(IOnSelectItem onSelectItem) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        View dialogView = context.getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_picker_style_2, null);
        WheelView wheelView1 = dialogView.findViewById(R.id.wheel1);
        Button cancel = dialogView.findViewById(R.id.cancel);
        Button ok = dialogView.findViewById(R.id.ok);
        cancel.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        ok.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();
            onSelectItem.onResult(items.get(wheelView1.getCurrentItem()));
        });
        View.OnTouchListener touchHandler = (v, e) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            v.performClick();
            return false;
        };
        wheelView1.setOnTouchListener(touchHandler);
        WheelAdapter adapter1 = new WheelAdapter() {
            @Override
            public int getItemsCount() {
                return items.size();
            }

            @Override
            public Object getItem(int index) {
                return render.onRender((T) items.get(index));
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }
        };
        wheelView1.setCyclic(isCyclic);
        wheelView1.setGravity(Gravity.CENTER);
        wheelView1.setAdapter(adapter1);
        wheelView1.setCurrentItem(0);
        mBottomSheetDialog.setContentView(dialogView);
        mBottomSheetDialog.show();
    }

    interface IOnSelectItem {
        void onResult(Object obj);
    }

    interface IDataRender<T> {
        String onRender(T obj);
    }
}
