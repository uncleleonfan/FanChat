package com.itheima.leon.qqdemo.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.app.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 10:18
 * 描述：    TODO
 */
public class ChatActivity extends BaseActivity {
    public static final String TAG = "ChatActivity";
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        super.init();
        mBack.setVisibility(View.VISIBLE);
        String userName = getIntent().getStringExtra(Constant.ExtraKey.USER_NAME);
        String title = String.format(getString(R.string.chat_with), userName);
        mTitle.setText(title);
    }

    @OnClick(R.id.back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
