package com.itheima.leon.qqdemo.presenter.impl;

import com.itheima.leon.qqdemo.presenter.RegisterPresenter;
import com.itheima.leon.qqdemo.utils.StringUtils;
import com.itheima.leon.qqdemo.view.RegisterView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 22:21
 * 描述：    TODO
 */
public class RegisterPresenterImpl implements RegisterPresenter {
    public static final String TAG = "RegisterPresenterImpl";

    public RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void register(String userName, String pwd, String pwdConfirm) {
        if (StringUtils.checkUserName(userName)) {
            if (StringUtils.checkPassword(pwd)) {
                if (pwd.equals(pwdConfirm)) {
                    mRegisterView.onStartRegister();
                } else {
                    mRegisterView.onConfirmPasswordError();
                }
            } else {
                mRegisterView.onPasswordError();
            }
        } else {
            mRegisterView.onUserNameError();
        }

    }
}
