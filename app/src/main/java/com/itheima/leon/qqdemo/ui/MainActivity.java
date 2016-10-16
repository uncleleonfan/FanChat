package com.itheima.leon.qqdemo.ui;

import android.content.Intent;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.service.TestService;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }


    @Override
    protected void init() {
        super.init();
        startTestService();
    }

    private void startTestService() {
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }
}
