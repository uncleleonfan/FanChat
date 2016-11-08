package com.itheima.leon.qqdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.model.ContactListItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 12:17
 * 描述：    TODO
 */
public class ContactListItemView extends RelativeLayout {
    public static final String TAG = "ContactItemView";
    @BindView(R.id.section)
    TextView mSection;
    @BindView(R.id.user_name)
    TextView mUserName;

    public ContactListItemView(Context context) {
        this(context, null);
    }

    public ContactListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_contact_item, this);
        ButterKnife.bind(this, this);

    }

    public void bindView(ContactListItem contactListItem) {
        mUserName.setText(contactListItem.userName);
        if (contactListItem.showFirstLetter) {
            mSection.setVisibility(VISIBLE);
            mSection.setText(contactListItem.getFirstLetterString());
        } else {
            mSection.setVisibility(GONE);
        }
    }
}
