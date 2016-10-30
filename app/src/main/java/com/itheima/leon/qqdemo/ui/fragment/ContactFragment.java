package com.itheima.leon.qqdemo.ui.fragment;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.ContactListAdapter;
import com.itheima.leon.qqdemo.adpater.EMContactListenerAdapter;
import com.itheima.leon.qqdemo.app.Constant;
import com.itheima.leon.qqdemo.model.ContactItem;
import com.itheima.leon.qqdemo.presenter.ContactPresenter;
import com.itheima.leon.qqdemo.presenter.impl.ContactPresenterImpl;
import com.itheima.leon.qqdemo.ui.activity.AddFriendActivity;
import com.itheima.leon.qqdemo.ui.activity.ChatActivity;
import com.itheima.leon.qqdemo.view.ContactView;
import com.itheima.leon.qqdemo.widget.SlideBar;

import java.util.List;

import butterknife.BindView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:37
 * 描述：    TODO
 */
public class ContactFragment extends BaseFragment implements ContactView {
    public static final String TAG = "ContactFragment";
    private static final int POSITION_NOT_FOUND = -1;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.add)
    ImageView mAdd;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.slide_bar)
    SlideBar mSlideBar;
    @BindView(R.id.section)
    TextView mSection;

    private ContactListAdapter mContactListAdapter;


    private ContactPresenter mContactPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        super.init();
        mContactPresenter = new ContactPresenterImpl(this);
        initView();
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
        mContactPresenter.getContactsFromServer();
    }

    private void initView() {
        mTitle.setText(getString(R.string.contacts));
        mAdd.setVisibility(View.VISIBLE);
        mAdd.setOnClickListener(mOnAddFriendListener);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mContactListAdapter = new ContactListAdapter(getContext(), mContactPresenter.getContactList());
        mContactListAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mContactListAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_blue, R.color.qq_red);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mSlideBar.setOnSlidingBarChangeListener(mOnSlideBarChangeListener);
    }

    @Override
    public void onGetContactListSuccess() {
        mContactListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetContactListFailed() {
        toast(getString(R.string.get_contacts_error));
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDeleteFriendSuccess() {
        hideProgress();
        toast(getString(R.string.delete_friend_success));
        mContactPresenter.refreshContactList();
    }

    @Override
    public void onDeleteFriendFailed() {
        hideProgress();
        toast(getString(R.string.delete_friend_failed));
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mContactPresenter.refreshContactList();
        }
    };

    private SlideBar.OnSlideBarChangeListener mOnSlideBarChangeListener = new SlideBar.OnSlideBarChangeListener() {
        @Override
        public void onSectionChange(int index, String section) {
            mSection.setVisibility(View.VISIBLE);
            mSection.setText(section);
            scrollToSection(section);
        }

        @Override
        public void onSlidingFinish() {
            mSection.setVisibility(View.GONE);
        }
    };

    private void scrollToSection(String section) {
        int sectionPosition = getSectionPosition(section);
        mRecyclerView.scrollToPosition(sectionPosition);
        if (sectionPosition != POSITION_NOT_FOUND) {
            mRecyclerView.smoothScrollToPosition(sectionPosition);
        }
    }


    private int getSectionPosition(String section) {
        List<ContactItem> contactItems = mContactListAdapter.getContactItems();
        for (int i = 0; i < contactItems.size(); i++) {
            if (section.equals(contactItems.get(i).getFirstLetterString())) {
                return i;
            }
        }
        return POSITION_NOT_FOUND;
    }

    private View.OnClickListener mOnAddFriendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(AddFriendActivity.class);
        }
    };

    private EMContactListenerAdapter mEMContactListener = new EMContactListenerAdapter() {

        @Override
        public void onContactAdded(String s) {
            mContactPresenter.refreshContactList();
        }

        @Override
        public void onContactDeleted(String s) {
            mContactPresenter.refreshContactList();
        }

    };

    private ContactListAdapter.OnItemClickListener mOnItemClickListener = new ContactListAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(int index, String name) {
            startActivity(ChatActivity.class, Constant.Extra.USER_NAME, name);
        }

        @Override
        public void onItemLongClick(int index, final String name) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            String message = String.format(getString(R.string.delete_friend_message), name);
            builder.setTitle(getString(R.string.delete_friend))
                    .setMessage(message)
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showProgress(getString(R.string.deleting_friend));
                            mContactPresenter.deleteFriend(name);

                        }
                    });
            builder.show();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().contactManager().removeContactListener(mEMContactListener);
    }
}
