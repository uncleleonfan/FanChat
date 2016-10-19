package com.itheima.leon.qqdemo.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;

import butterknife.BindView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 14:06
 * 描述：    TODO
 */
public class AddFriendActivity extends BaseActivity {
    public static final String TAG = "AddFriendActivity";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        super.init();
        mTitle.setText(getString(R.string.add_friend));
    }
}
