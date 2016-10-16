package com.itheima.leon.qqdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/15 22:11
 * 描述：    TODO
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        init();
    }

    protected void init() {}

    public abstract int getLayoutRes();
}
