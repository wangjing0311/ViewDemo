package com.ylw.viewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.ylw.viewdemo.views.PaddingShadowLayout;

public class ViewShadowActivity extends AppCompatActivity {

    private View control1;
    private View control2;
    private PaddingShadowLayout shadowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shadow);

        control1 = findViewById(R.id.view);
        control2 = findViewById(R.id.view2);
        shadowLayout = findViewById(R.id.shadow_layout);

        control1.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            shadowLayout.setControl1(Math.min(control1.getWidth(), Math.max(0, event.getX())) / control1.getWidth(),
                    Math.min(control1.getHeight(), Math.max(0, event.getY())) / control1.getHeight());
            return true;
        });
        control2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            shadowLayout.setControl2(Math.min(control2.getWidth(), Math.max(0, event.getX())) / control2.getWidth(),
                    Math.min(control2.getHeight(), Math.max(0, event.getY())) / control2.getHeight());
            return true;
        });
    }
}
