package com.itheima.leon.qqdemo.utils;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 21:18
 * 描述：    TODO
 */
public class StringUtils {

    private static final String USER_NAME_REGEX = "^[a-zA-Z]\\w{2,19}$";

    private static final String PASSWORD_REGEX = "^[0-9]{3,20}$";


    public static boolean checkUserName(String userName) {
        return userName.matches(USER_NAME_REGEX);
    }

    public static boolean checkPassword(String pwd) {
        return pwd.matches(PASSWORD_REGEX);
    }
}
