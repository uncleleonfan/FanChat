package com.itheima.leon.qqdemo.view;

import com.itheima.leon.qqdemo.model.User;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 22:05
 * 描述：    TODO
 */

public interface RegisterView {

    void onStartRegister();

    void onRegisterError();

    void onResisterUserExist();

    void onRegisterSuccess(User user);

    void onUserNameError();

    void onPasswordError();

    void onConfirmPasswordError();

}
