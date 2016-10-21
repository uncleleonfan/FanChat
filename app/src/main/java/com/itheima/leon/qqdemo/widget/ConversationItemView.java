package com.itheima.leon.qqdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itheima.leon.qqdemo.R;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/21 10:20
 * 描述：    TODO
 */
public class ConversationItemView extends RelativeLayout {
    public static final String TAG = "ConversationItemView";

    public ConversationItemView(Context context) {
        this(context, null);
    }

    public ConversationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_conversation_item, this);
    }
}
