package com.itheima.leon.qqdemo.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.itheima.leon.qqdemo.adpater.EMCallBackAdapter;
import com.itheima.leon.qqdemo.presenter.DynamicPresenter;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.DynamicView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 10:18
 * 描述：    TODO
 */
public class DynamicPresenterImpl implements DynamicPresenter {

    private DynamicView mDynamicView;

    public DynamicPresenterImpl(DynamicView dynamicView) {
        mDynamicView = dynamicView;
    }

    @Override
    public void logout() {
        mDynamicView.onStartLogout();
        EMClient.getInstance().logout(true, mEMCallBackAdapter);
    }

    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDynamicView.onLogoutSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDynamicView.onLogoutFailed();
                }
            });
        }
    };
}
