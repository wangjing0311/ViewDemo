package com.ylw.viewdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ylw.viewdemo.views.CopyMenuTextView;
import com.ylw.viewdemo.views.CopyPopWindow;

public class LongPressCopyActivity extends AppCompatActivity {

    private static final String TAG = "LongPressCopyActivity";
    private CopyMenuTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_press_copy);

        textView = findViewById(R.id.editText);
        textView.setOnClickListener((v) -> {
            new CopyPopWindow().show(textView);
        });
    }

}
