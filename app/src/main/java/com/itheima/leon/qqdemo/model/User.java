package com.itheima.leon.qqdemo.model;

import cn.bmob.v3.BmobUser;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 23:31
 * 描述：    TODO
 */
public class User extends BmobUser {

    public User(String userName, String password) {
        setUsername(userName);
        setPassword(password);
    }

}
