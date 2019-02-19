package com.ylw.viewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        NumberPicker picker = findViewById(R.id.num_picker);

        picker.setMaxValue(100);
        picker.setMinValue(0);
        picker.setValue(2);
    }

    public void showTimePickerDialog(View view) {
        new BottomSheetDialogTimePicker(this).show(date -> {
            Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    public void showItemPicker(View view) {
        List<String> items = new ArrayList<>();
        items.add("语文");
        items.add("英语");
        items.add("物理");
        items.add("化学");
        items.add("生物");
        items.add("历史");
        items.add("地理");
        items.add("政治");
        items.add("文综");
        items.add("理综");
        items.add("数学");
        new BottomSheetDialogPickerOne<String>(this)
                .setRender((obj) -> obj)
                .setData(items)
                .setCyclic(true)
                .show(obj -> {
                    Toast.makeText(MainMenuActivity.this, (String) obj, Toast.LENGTH_SHORT).show();
                });
    }

    public void showDateRangePicker(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);
        new BottomSheetDialogTimeRangePicker(this)
                .setStartDate(new Date())
                .setEndDate(calendar.getTime())
                .show((start, end) -> {
                    Toast.makeText(this, start.toString() + " - " + end.toString(), Toast.LENGTH_SHORT).show();
                });
    }
}
