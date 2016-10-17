package com.itheima.leon.qqdemo.ui.fragment;

import android.widget.TextView;

import com.itheima.leon.qqdemo.R;

import butterknife.BindView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:37
 * 描述：    TODO
 */
public class ContactFragment extends BaseFragment {
    public static final String TAG = "ContactFragment";
    @BindView(R.id.test)
    TextView mTest;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base;
    }

    @Override
    protected void init() {
        super.init();
        mTest.setText("联系人");
    }
}
