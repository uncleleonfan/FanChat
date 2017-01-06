package com.itheima.leon.qqdemo.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itheima.leon.qqdemo.database.DatabaseManager;
import com.itheima.leon.qqdemo.model.ContactListItem;
import com.itheima.leon.qqdemo.presenter.ContactPresenter;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.ContactView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 15:34
 * 描述：    TODO
 */
public class ContactPresenterImpl implements ContactPresenter {
    private static final String TAG = "ContactPresenterImpl";

    private ContactView mContactView;

    private List<ContactListItem> mContactListItems;

    public ContactPresenterImpl(ContactView contactView) {
        mContactView = contactView;
        mContactListItems = new ArrayList<ContactListItem>();
    }


    @Override
    public List<ContactListItem> getContactList() {
        return mContactListItems;
    }

    @Override
    public void refreshContactList() {
        mContactListItems.clear();
        getContactsFromServer();
    }

    @Override
    public void deleteFriend(final String name) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(name);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onDeleteFriendSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onDeleteFriendFailed();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getContactsFromServer() {
        if (mContactListItems.size() > 0) {
            mContactView.onGetContactListSuccess();
            return;
        }
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    startGetContactList();
                    notifyGetContactListSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    notifyGetContactListFailed();
                }
            }
        });

    }

    private void notifyGetContactListFailed() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactView.onGetContactListFailed();
            }
        });
    }

    /**
     * 获取联系人列表数据
     * @throws HyphenateException
     */
    private void startGetContactList() throws HyphenateException {
        List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
        Collections.sort(contacts, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(0) - o2.charAt(0);//ascending order
            }
        });
        DatabaseManager.getInstance().deleteAllContacts();
        if (!contacts.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
                ContactListItem item = new ContactListItem();
                item.userName = contacts.get(i);
                if (itemInSameGroup(i, item)) {
                    item.showFirstLetter = false;
                }
                mContactListItems.add(item);
                saveContactToDatabase(item.userName);
            }
        }
    }

    private void saveContactToDatabase(String userName) {
        DatabaseManager.getInstance().saveContact(userName);
    }

    /**
     * 当前联系人跟上个联系人比较，如果首字符相同则返回true
     * @param i 当前联系人下标
     * @param item 当前联系人数据模型
     * @return true 表示当前联系人和上一联系人在同一组
     */
    private boolean itemInSameGroup(int i, ContactListItem item) {
        return i > 0 && (item.getFirstLetter() == mContactListItems.get(i - 1).getFirstLetter());
    }

    private void notifyGetContactListSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactView.onGetContactListSuccess();
            }
        });
    }
}
