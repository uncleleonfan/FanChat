package com.itheima.leon.qqdemo.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.itheima.leon.qqdemo.widget.ReceiveMessageItemView;
import com.itheima.leon.qqdemo.widget.SendMessageItemView;

import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 12:42
 * 描述：    TODO
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "ChatAdapter";

    private Context mContext;

    private List<EMMessage> mMessages;

    private static final int ITEM_TYPE_SEND_MESSAGE = 0;
    private static final int ITEM_TYPE_RECEIVE_MESSAGE = 1;

    public ChatAdapter(Context context, List<EMMessage> messages) {
        mContext = context;
        mMessages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_SEND_MESSAGE) {
            return new SendItemViewHolder(new SendMessageItemView(mContext));
        } else {
            return new ReceiveItemViewHolder(new ReceiveMessageItemView(mContext));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SendItemViewHolder) {
            ((SendItemViewHolder) holder).mSendMessageItemView.bindView(mMessages.get(position));
        } else {
            ((ReceiveItemViewHolder) holder).mReceiveMessageItemView.bindView(mMessages.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage message = mMessages.get(position);
        return message.direct() == EMMessage.Direct.SEND ? ITEM_TYPE_SEND_MESSAGE : ITEM_TYPE_RECEIVE_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ReceiveItemViewHolder extends RecyclerView.ViewHolder{

        public ReceiveMessageItemView mReceiveMessageItemView;

        public ReceiveItemViewHolder(ReceiveMessageItemView itemView) {
            super(itemView);
            mReceiveMessageItemView = itemView;
        }
    }

    public class SendItemViewHolder extends RecyclerView.ViewHolder {

        public SendMessageItemView mSendMessageItemView;

        public SendItemViewHolder(SendMessageItemView itemView) {
            super(itemView);
            mSendMessageItemView = itemView;
        }
    }
}
