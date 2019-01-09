package com.ylw.viewdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by 袁立位 on 2019/1/7 9:15.
 */
public class ViewApplication extends Application {

    private static final String TAG = "ViewApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
