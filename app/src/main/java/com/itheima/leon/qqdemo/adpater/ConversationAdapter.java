package com.itheima.leon.qqdemo.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;
import com.itheima.leon.qqdemo.widget.ConversationItemView;

import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/21 10:11
 * 描述：    TODO
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationItemViewHolder> {
    public static final String TAG = "ConversationAdapter";

    public Context mContext;
    public List<EMConversation> mEMConversations;

    public ConversationAdapter(Context context, List<EMConversation> conversations) {
        mContext = context;
        mEMConversations = conversations;
    }

    @Override
    public ConversationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConversationItemViewHolder(new ConversationItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ConversationItemViewHolder holder, int position) {
        holder.mConversationItemView.bindView(mEMConversations.get(position));
    }

    @Override
    public int getItemCount() {
        return mEMConversations.size();
    }


    public class ConversationItemViewHolder extends RecyclerView.ViewHolder{

        public ConversationItemView mConversationItemView;

        public ConversationItemViewHolder(ConversationItemView itemView) {
            super(itemView);
            mConversationItemView = itemView;
        }
    }
}
