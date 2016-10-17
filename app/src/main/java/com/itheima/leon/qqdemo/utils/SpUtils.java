package com.itheima.leon.qqdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 10:52
 * 描述：    TODO
 */
public class SpUtils {
    public static final String TAG = "SpUtils";

    private static final String FILE_NAME = "qqdemo";


    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static void saveStrings(Context context, String keys[], String values[]) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        for (int i = 0; i < keys.length; i++) {
            edit.putString(keys[i], values[i]);
        }
        edit.apply();
    }
}
