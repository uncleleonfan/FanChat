package com.itheima.leon.qqdemo.model;

import com.itheima.leon.qqdemo.R;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 12:10
 * 描述：    TODO
 */
public class ContactItem {
    public static final String TAG = "ContactItem";

    public int avatar = R.mipmap.avatar6;

    public String userName;

    public boolean showSection = true;

    public char getFirstLetter() {
        return userName.charAt(0);
    }

    public String getFirstLetterString() {
        return String.valueOf(getFirstLetter()).toUpperCase();
    }
}
