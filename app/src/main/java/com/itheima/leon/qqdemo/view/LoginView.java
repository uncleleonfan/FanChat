package com.itheima.leon.qqdemo.view;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 21:13
 * 描述：    TODO
 */
public interface LoginView {
    public static final String TAG = "LoginView";

    void onUserNameError();

    void onPasswordError();

    void onStartLogin();

    void onLoginSuccess();

    void onLoginFailed();


}
