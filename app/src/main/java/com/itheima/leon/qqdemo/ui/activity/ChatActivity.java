package com.itheima.leon.qqdemo.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.ChatAdapter;
import com.itheima.leon.qqdemo.adpater.TextWatcherAdapter;
import com.itheima.leon.qqdemo.app.Constant;
import com.itheima.leon.qqdemo.presenter.ChatPresenter;
import com.itheima.leon.qqdemo.presenter.impl.ChatPresenterImpl;
import com.itheima.leon.qqdemo.view.ChatView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/20 10:18
 * 描述：    TODO
 */
public class ChatActivity extends BaseActivity implements ChatView{
    public static final String TAG = "ChatActivity";
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.edit)
    EditText mEdit;
    @BindView(R.id.send)
    Button mSend;

    private ChatPresenter mChatPresenter;
    private String mUserName;

    private ChatAdapter mChatAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        super.init();
        mChatPresenter = new ChatPresenterImpl(this);
        mBack.setVisibility(View.VISIBLE);
        mUserName = getIntent().getStringExtra(Constant.ExtraKey.USER_NAME);
        String title = String.format(getString(R.string.chat_with), mUserName);
        mTitle.setText(title);
        mEdit.setOnEditorActionListener(mOnEditorActionListener);
        mEdit.addTextChangedListener(mTextWatcher);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatAdapter = new ChatAdapter(this, mChatPresenter.getMessages());
        mRecyclerView.setAdapter(mChatAdapter);
    }

    @OnClick({R.id.back, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        mChatPresenter.sendMessage(mUserName, mEdit.getText().toString().trim());
        hideKeyBoard();
        mEdit.getText().clear();
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        }
    };

    private TextWatcherAdapter mTextWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
/*            if (s.length() == 0) {
                mSend.setEnabled(false);
            } else {
                mSend.setEnabled(true);
            }*/
            mSend.setEnabled(s.length() != 0);
        }
    };


    @Override
    public void onStartSendMessage() {
//        showProgress(getString(R.string.sending));
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSendMessageSuccess() {
        hideProgress();
        toast(getString(R.string.send_success));
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSendMessageFailed() {
        hideProgress();
        toast(getString(R.string.send_failed));
    }
}
