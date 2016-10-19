package com.itheima.leon.qqdemo.view;

import com.itheima.leon.qqdemo.model.AddFriendItem;
import com.itheima.leon.qqdemo.model.User;

import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 14:40
 * 描述：    TODO
 */
public interface AddFriendView {
    public static final String TAG = "AddFriendView";

    void onStartSearch();

    void onSearchSuccess(List<AddFriendItem> list);

    void onSearchFailed();
}
