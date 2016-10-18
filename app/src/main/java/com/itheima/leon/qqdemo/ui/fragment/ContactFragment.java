package com.itheima.leon.qqdemo.ui.fragment;

import android.view.View;
import android.widget.ImageView;
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
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.add)
    ImageView mAdd;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        super.init();
        mTest.setText("联系人");
        mTitle.setText(getString(R.string.contacts));
        mAdd.setVisibility(View.VISIBLE);
    }

}
