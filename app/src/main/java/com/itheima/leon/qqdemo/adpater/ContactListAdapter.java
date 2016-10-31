package com.itheima.leon.qqdemo.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.leon.qqdemo.model.ContactItem;
import com.itheima.leon.qqdemo.widget.ContactItemView;

import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 12:06
 * 描述：    TODO
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder> {
    public static final String TAG = "ContactListAdapter";

    private Context mContext;
    private List<ContactItem> mContactItems;
    private OnItemClickListener mOnItemClickListener;

    public ContactListAdapter(Context context, List<ContactItem> items) {
        mContext = context;
        mContactItems = items;
    }

    @Override
    public ContactItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContactItemView itemView = new ContactItemView(mContext);
        return new ContactItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {
        final ContactItem item = mContactItems.get(position);
        holder.mItemView.bindView(item);
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(item.userName);
                }
            }
        });
        holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemLongClick(item.userName);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mContactItems == null) {
            return 0;
        }
        return mContactItems.size();
    }

    public class ContactItemViewHolder extends RecyclerView.ViewHolder {

        public ContactItemView mItemView;

        public ContactItemViewHolder(ContactItemView itemView) {
            super(itemView);
            mItemView = itemView;
        }
    }

    public List<ContactItem> getContactItems() {
        return mContactItems;
    }


    public interface OnItemClickListener {
        void onItemClick(String name);
        void onItemLongClick(String name);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }
}
