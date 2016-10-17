package com.itheima.leon.qqdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 21:52
 * 描述：    TODO
 */
public abstract class BaseFragment extends Fragment {
    public static final String TAG = "BaseFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutRes(), null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }

    protected void init() {
        Log.d(TAG, "init: ");
    };

    protected abstract int getLayoutRes();
}
