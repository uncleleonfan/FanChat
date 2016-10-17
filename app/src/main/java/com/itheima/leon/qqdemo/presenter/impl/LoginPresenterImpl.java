package com.itheima.leon.qqdemo.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.itheima.leon.qqdemo.adpater.EMCallBackAdapter;
import com.itheima.leon.qqdemo.presenter.LoginPresenter;
import com.itheima.leon.qqdemo.utils.StringUtils;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
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
        if (StringUtils.checkUserName(userName)) {
            if (StringUtils.checkPassword(pwd)) {
                //动态权限管理
                mLoginView.onStartLogin();
                startLogin(userName, pwd);
            } else {
                mLoginView.onPasswordError();
            }
        } else {
            mLoginView.onUserNameError();
        }
    }

    private void startLogin(String userName, String pwd) {
        EMClient.getInstance().login(userName, pwd, mEMCallBack);
    }

    private EMCallBackAdapter mEMCallBack = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginView.onLoginSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginView.onLoginFailed();
                }
            });
        }
    };
}
