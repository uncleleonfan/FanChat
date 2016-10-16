package com.itheima.leon.qqdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/15 22:11
 * 描述：    TODO
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        init();
    }

    protected void init() {}

    public abstract int getLayoutRes();

    protected void startActivity(Class activity) {
        startActivity(activity, false);
    }

    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    protected void postDelay(Runnable runnable, long millis) {
        mHandler.postDelayed(runnable, millis);
    }

}
