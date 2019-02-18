package com.ylw.viewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

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

    public void timePickerDialog(View view) {
        Toast.makeText(this, "hhhh", Toast.LENGTH_SHORT).show();
        showPickDialog();
    }

    void showPickDialog() {
        new TimePickerDialog(this).showMMDDHHmm(d -> {
            Toast.makeText(this, d.toString(), Toast.LENGTH_SHORT).show();
        });
//        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
////        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
//        View dialogView = pvTime;
//        Button btn_dialog_bottom_sheet_ok = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
//        Button btn_dialog_bottom_sheet_cancel = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
//        ImageView img_bottom_dialog = dialogView.findViewById(R.id.img_bottom_dialog);
//        Glide.with(getContext()).load(R.drawable.bottom_dialog).into(img_bottom_dialog);
//        mBottomSheetDialog.setContentView(dialogView);
//
//        btn_dialog_bottom_sheet_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBottomSheetDialog.dismiss();
//            }
//        });
//        btn_dialog_bottom_sheet_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mBottomSheetDialog.dismiss();
//            }
//        });
//        mBottomSheetDialog.show();
    }
}
