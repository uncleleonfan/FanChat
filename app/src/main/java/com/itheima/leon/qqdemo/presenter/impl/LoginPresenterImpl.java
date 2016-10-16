package com.itheima.leon.qqdemo.presenter.impl;

import com.itheima.leon.qqdemo.presenter.LoginPresenter;
import com.itheima.leon.qqdemo.utils.LoginUtils;
import com.itheima.leon.qqdemo.view.LoginView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 21:17
 * 描述：    TODO
 */
public class LoginPresenterImpl implements LoginPresenter {
    public static final String TAG = "LoginPresenterImpl";


    public LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(String userName, String pwd) {
        if (LoginUtils.checkUserName(userName)) {
            if (LoginUtils.checkPassword(pwd)) {
                mLoginView.onStartLogin();
                startLogin();
            } else {
                mLoginView.onPasswordError();
            }
        } else {
            mLoginView.onUserNameError();
        }
    }

    private void startLogin() {

    }
}
