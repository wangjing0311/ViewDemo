package com.ylw.viewdemo;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenDialogTestActivity extends AppCompatActivity {

    private static final String TAG = "FullScreenDialog";
    private Button button6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_dialog_test);

        button6 = findViewById(R.id.button6);

        button6.setOnClickListener(v -> {
            Dialog dig = new Dialog(this, R.style.FullScreenDialog);
            dig.setContentView(R.layout.dialog_layout);
            dig.show();
        });
    }
}
