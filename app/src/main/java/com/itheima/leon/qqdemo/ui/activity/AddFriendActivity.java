package com.itheima.leon.qqdemo.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.AddFriendListAdapter;
import com.itheima.leon.qqdemo.model.AddFriendItem;
import com.itheima.leon.qqdemo.presenter.AddFriendPresenter;
import com.itheima.leon.qqdemo.presenter.impl.AddFriendPresenterImpl;
import com.itheima.leon.qqdemo.view.AddFriendView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 14:06
 * 描述：    TODO
 */
public class AddFriendActivity extends BaseActivity implements AddFriendView {
    public static final String TAG = "AddFriendActivity";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.friend_not_found)
    TextView mFriendNotFound;

    private AddFriendPresenter mAddFriendPresenter;

    private AddFriendListAdapter mAddFriendListAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        super.init();
        mAddFriendPresenter = new AddFriendPresenterImpl(this);
        mTitle.setText(getString(R.string.add_friend));
        mUserName.setOnEditorActionListener(mOnEditorActionListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchFriend(mUserName.getText().toString().trim());
                return true;
            }
            return false;
        }
    };


    @OnClick(R.id.search)
    public void onClick() {
        searchFriend(mUserName.getText().toString().trim());
        hideKeyBoard();
    }

    private void searchFriend(String keyword) {
        mAddFriendPresenter.searchFriend(keyword);
    }

    @Override
    public void onStartSearch() {
        showProgress(getString(R.string.searching));
    }

    @Override
    public void onSearchSuccess(List<AddFriendItem> list) {
        hideProgress();
        mFriendNotFound.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mAddFriendListAdapter = new AddFriendListAdapter(this, list);
        mRecyclerView.setAdapter(mAddFriendListAdapter);
    }

    @Override
    public void onSearchFailed() {
        hideProgress();
        mFriendNotFound.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onAddFriendSuccess() {
        toast(getString(R.string.add_friend_success));
    }

    @Override
    public void onAddFriendFailed() {
        toast(getString(R.string.add_friend_failed));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddFriendPresenter.onDestroy();
    }
}
