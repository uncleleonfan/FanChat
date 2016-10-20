package com.itheima.leon.qqdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.hyphenate.chat.EMMessage;
import com.itheima.leon.qqdemo.R;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 12:47
 * 描述：    TODO
 */
public class ReceiveMessageItemView extends RelativeLayout {

    public ReceiveMessageItemView(Context context) {
        this(context, null);
    }

    public ReceiveMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_receive_message_item, this);
    }

    public void bindView(EMMessage emMessage) {

    }
}
