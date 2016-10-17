package com.itheima.leon.qqdemo.ui.fragment;

import android.widget.TextView;

import com.itheima.leon.qqdemo.R;

import butterknife.BindView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:39
 * 描述：    TODO
 */
public class DynamicFragment extends BaseFragment {
    public static final String TAG = "DynamicFragment";
    @BindView(R.id.test)
    TextView mTest;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base;
    }

    @Override
    protected void init() {
        super.init();
        mTest.setText("动态");
        mTitle.setText(getString(R.string.dynamic));

    }
}
