package com.itheima.leon.qqdemo.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itheima.leon.qqdemo.database.DatabaseManager;
import com.itheima.leon.qqdemo.model.ContactItem;
import com.itheima.leon.qqdemo.presenter.ContactPresenter;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.ContactView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 15:34
 * 描述：    TODO
 */
public class ContactPresenterImpl implements ContactPresenter {
    private static final String TAG = "ContactPresenterImpl";

    private ContactView mContactView;

    private List<ContactItem> mContactItems;

    public ContactPresenterImpl(ContactView contactView) {
        mContactView = contactView;
        mContactItems = new ArrayList<ContactItem>();
    }


    @Override
    public List<ContactItem> getContactList() {
        return mContactItems;
    }

    @Override
    public void refreshContactList() {
        mContactItems.clear();
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
        if (mContactItems.size() > 0) {
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

    private void startGetContactList() throws HyphenateException {
        List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
        DatabaseManager.getInstance().deleteAllContacts();
        if (!contacts.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
                ContactItem item = new ContactItem();
                item.userName = contacts.get(i);
                if (itemInSameGroup(i, item)) {
                    item.showSection = false;
                }
                mContactItems.add(item);
                saveContactToDatabase(item.userName);
            }
        }
    }

    private void saveContactToDatabase(String userName) {
        DatabaseManager.getInstance().saveContact(userName);
    }

    private boolean itemInSameGroup(int i, ContactItem item) {
        return i > 0 && (item.getFirstLetter() == mContactItems.get(i - 1).getFirstLetter());
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
