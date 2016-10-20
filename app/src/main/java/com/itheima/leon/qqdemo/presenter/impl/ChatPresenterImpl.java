package com.itheima.leon.qqdemo.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.itheima.leon.qqdemo.adpater.EMCallBackAdapter;
import com.itheima.leon.qqdemo.presenter.ChatPresenter;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.ChatView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 11:24
 * 描述：    TODO
 */
public class ChatPresenterImpl implements ChatPresenter {
    public static final String TAG = "ChatPresenterImpl";

    private ChatView mChatView;

    private List<EMMessage> mEMMessageList;


    public ChatPresenterImpl(ChatView chatView) {
        mChatView = chatView;
        mEMMessageList = new ArrayList<EMMessage>();
    }

    @Override
    public void sendMessage(final String userName, final String message) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                EMMessage emMessage = EMMessage.createTxtSendMessage(message, userName);
                emMessage.setStatus(EMMessage.Status.INPROGRESS);
                emMessage.setMessageStatusCallback(mEMCallBackAdapter);
                mEMMessageList.add(emMessage);
                EMClient.getInstance().chatManager().sendMessage(emMessage);
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onStartSendMessage();
                    }
                });
            }
        });
    }

    @Override
    public List<EMMessage> getMessages() {
        return mEMMessageList;
    }

    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter() {
        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.onSendMessageSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mChatView.onSendMessageFailed();
                }
            });
        }
    };
}
