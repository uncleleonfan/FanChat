package com.itheima.leon.qqdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.model.AddFriendItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 15:48
 * 描述：    TODO
 */
public class AddFriendItemView extends RelativeLayout {
    public static final String TAG = "AddFriendItemView";
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.timestamp)
    TextView mTimestamp;


    public AddFriendItemView(Context context) {
        this(context, null);
    }

    public AddFriendItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_add_friend_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(AddFriendItem addFriendItem) {
        mUserName.setText(addFriendItem.userName);
        mTimestamp.setText(addFriendItem.timestamp);
    }
}
