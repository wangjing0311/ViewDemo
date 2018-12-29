package com.ylw.viewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Button button3;
    private TextView textView;
    private TextView textView2;
    private Button button4;
    private Button button5;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        imageView = findViewById(R.id.imageView);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            int[] location = new int[2];
//            imageView.getLocationOnScreen(location);
//            new GuideDialog(R.layout.guide_01, R.id.focus,
//                    button2,
//                    button3, button4).onClick(R.id.button7, (v) -> {
//
//            }).show();
//        }, 100);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
