package com.itheima.leon.qqdemo.factory;

import com.itheima.leon.qqdemo.ui.fragment.BaseFragment;
import com.itheima.leon.qqdemo.ui.fragment.ContactFragment;
import com.itheima.leon.qqdemo.ui.fragment.DynamicFragment;
import com.itheima.leon.qqdemo.ui.fragment.MessageFragment;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:05
 * 描述：    TODO
 */
public class FragmentFactory {
    public static final String TAG = "FragmentFactory";

    private static FragmentFactory sFragmentFactory;

    private BaseFragment mMessageFragment;
    private BaseFragment mContactFragment;



    private BaseFragment mDynamicFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public BaseFragment getMessageFragment() {
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        return mMessageFragment;
    }

    public BaseFragment getDynamicFragment() {
        if (mDynamicFragment == null) {
            mDynamicFragment = new DynamicFragment();
        }
        return mDynamicFragment;
    }

    public BaseFragment getContactFragment() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }




}
