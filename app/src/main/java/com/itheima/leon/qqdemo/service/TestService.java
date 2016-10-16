package com.itheima.leon.qqdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 9:56
 * 描述：    TODO
 */
public class TestService extends Service {
    public static final String TAG = "TestService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }
}
