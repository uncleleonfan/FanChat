package com.itheima.leon.qqdemo.presenter.impl;

import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itheima.leon.qqdemo.event.AddFriendEvent;
import com.itheima.leon.qqdemo.model.AddFriendItem;
import com.itheima.leon.qqdemo.model.User;
import com.itheima.leon.qqdemo.presenter.AddFriendPresenter;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.AddFriendView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 14:39
 * 描述：    TODO
 */
public class AddFriendPresenterImpl implements AddFriendPresenter {
    public static final String TAG = "AddFriendPresenterImpl";

    private AddFriendView mAddFriendView;

    public AddFriendPresenterImpl(AddFriendView addFriendView) {
        mAddFriendView = addFriendView;
        EventBus.getDefault().register(this);
    }

    @Override
    public void searchFriend(String keyword) {
        Log.d(TAG, "searchFriend: " + keyword);
        mAddFriendView.onStartSearch();
        //search friend
        //注:模糊查询只对付费用户开放，付费后可直接使用。
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereContains("username", keyword).addWhereNotEqualTo("username", EMClient.getInstance().getCurrentUser());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    List<AddFriendItem> listItems = new ArrayList<AddFriendItem>();
                    for (int i = 0; i <list.size(); i++) {
                        AddFriendItem item = new AddFriendItem();
                        item.timestamp = list.get(i).getCreatedAt();
                        item.userName = list.get(i).getUsername();
                        listItems.add(item);
                    }
                    mAddFriendView.onSearchSuccess(listItems);
                } else {
                    mAddFriendView.onSearchFailed();
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void addFriend(AddFriendEvent event){
        try {
            Log.d(TAG, "addFriend: " + event.getFriendName());
            EMClient.getInstance().contactManager().addContact(event.getFriendName(), event.getReason());
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.onAddFriendSuccess();
                }
            });
        } catch (HyphenateException e) {
            e.printStackTrace();
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.onAddFriendFailed();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

}
