package com.itheima.leon.qqdemo.presenter;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 11:23
 * 描述：    TODO
 */
public interface ChatPresenter {

    void sendMessage(String userName, String message);

    List<EMMessage> getMessages();

    void loadMessages(String userName);

    void loadMoreMessages(String userName);

    void makeMessageRead(String userName);
}
