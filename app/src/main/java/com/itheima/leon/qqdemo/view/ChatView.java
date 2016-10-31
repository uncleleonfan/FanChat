package com.itheima.leon.qqdemo.view;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 11:25
 * 描述：    TODO
 */
public interface ChatView {

    void onStartSendMessage();

    void onSendMessageSuccess();

    void onSendMessageFailed();

    void onMessagesLoaded();

    void onMoreMessagesLoaded(int size);

    void onNoMoreData();
}
