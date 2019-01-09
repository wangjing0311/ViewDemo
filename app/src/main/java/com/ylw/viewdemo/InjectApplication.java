package com.ylw.viewdemo;

import android.content.Context;
import android.util.Log;

/**
 * Created by 袁立位 on 2019/1/7 9:21.
 */
public class InjectApplication extends ViewApplication {

    private static final String TAG = "InjectApplication";

    @Override
    protected void attachBaseContext(Context base) {
        injectSign(base);
        super.attachBaseContext(base);
    }

    private void injectSign(Context base) {
        Log.i(TAG, "injectSign: ");
        
    }
}
