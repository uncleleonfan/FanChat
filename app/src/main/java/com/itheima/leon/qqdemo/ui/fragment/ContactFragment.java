package com.itheima.leon.qqdemo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.ContactListAdapter;
import com.itheima.leon.qqdemo.model.ContactItem;
import com.itheima.leon.qqdemo.presenter.ContactPresenter;
import com.itheima.leon.qqdemo.presenter.impl.ContactPresenterImpl;
import com.itheima.leon.qqdemo.view.ContactView;

import java.util.List;

import butterknife.BindView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:37
 * 描述：    TODO
 */
public class ContactFragment extends BaseFragment implements ContactView{
    public static final String TAG = "ContactFragment";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.add)
    ImageView mAdd;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ContactListAdapter mContactListAdapter;


    private ContactPresenter mContactPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        super.init();
        mTitle.setText(getString(R.string.contacts));
        mAdd.setVisibility(View.VISIBLE);
        initRecyclerView();
        mContactPresenter = new ContactPresenterImpl(this);
        mContactPresenter.getContactList();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onGetContactList(List<ContactItem> list) {
        mContactListAdapter = new ContactListAdapter(getContext(), list);
        mRecyclerView.setAdapter(mContactListAdapter);
    }

    @Override
    public void onGetContactListFailed() {
        toast(getString(R.string.get_contacts_error));
    }
}
