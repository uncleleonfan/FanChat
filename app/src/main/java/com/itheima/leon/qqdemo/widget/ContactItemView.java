package com.itheima.leon.qqdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.itheima.leon.qqdemo.R;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 12:17
 * 描述：    TODO
 */
public class ContactItemView extends RelativeLayout {
    public static final String TAG = "ContactItemView";

    public ContactItemView(Context context) {
        this(context, null);
    }

    public ContactItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_contact_item, this);
    }
}
