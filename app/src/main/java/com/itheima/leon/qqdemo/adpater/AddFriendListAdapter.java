package com.itheima.leon.qqdemo.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.itheima.leon.qqdemo.model.AddFriendItem;
import com.itheima.leon.qqdemo.widget.AddFriendItemView;

import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 16:09
 * 描述：    TODO
 */
public class AddFriendListAdapter extends RecyclerView.Adapter<AddFriendListAdapter.AddFriendItemViewHolder> {
    public static final String TAG = "AddFriendListAdapter";

    private Context mContext;
    private List<AddFriendItem> mAddFriendItemList;

    public AddFriendListAdapter(Context context, List<AddFriendItem> list) {
        mContext = context;
        mAddFriendItemList = list;
    }

    @Override
    public AddFriendItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        return new AddFriendItemViewHolder(new AddFriendItemView(mContext));
    }

    @Override
    public void onBindViewHolder(AddFriendItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.mAddFriendItemView.bindView(mAddFriendItemList.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        if (mAddFriendItemList == null) {
            return 0;
        }
        return mAddFriendItemList.size();
    }


    public class AddFriendItemViewHolder extends RecyclerView.ViewHolder {

        public AddFriendItemView mAddFriendItemView;

        public AddFriendItemViewHolder(AddFriendItemView itemView) {
            super(itemView);
            mAddFriendItemView = itemView;
        }
    }
}
