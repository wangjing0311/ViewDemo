package com.ylw.viewdemo.views;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.widget.TextView;

import java.util.logging.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by 袁立位 on 2019/1/4 17:58.
 */
public class CopyMenuTextView extends AppCompatTextView {

    private static final String TAG = "CopyMenuTextView";

    public CopyMenuTextView(Context context) {
        super(context);
    }

    public CopyMenuTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CopyMenuTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
