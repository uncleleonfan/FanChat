package com.itheima.leon.qqdemo.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.ChatAdapter;
import com.itheima.leon.qqdemo.adpater.EMMessageListenerAdapter;
import com.itheima.leon.qqdemo.adpater.TextWatcherAdapter;
import com.itheima.leon.qqdemo.app.Constant;
import com.itheima.leon.qqdemo.presenter.ChatPresenter;
import com.itheima.leon.qqdemo.presenter.impl.ChatPresenterImpl;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.ChatView;

import java.util.List;

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

    private LinearLayoutManager mLinearLayoutManager;

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

        initRecyclerView();

        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
        mChatPresenter.loadDataFromLocal(mUserName);

    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mChatAdapter = new ChatAdapter(this, mChatPresenter.getMessages());
        mRecyclerView.setAdapter(mChatAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
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
        updateList();
    }

    private void updateList() {
        mChatAdapter.notifyDataSetChanged();
        smoothScrollToBottom();
    }

    @Override
    public void onSendMessageSuccess() {
        hideProgress();
        toast(getString(R.string.send_success));
        updateList();
    }

    @Override
    public void onSendMessageFailed() {
        hideProgress();
        toast(getString(R.string.send_failed));
    }

    @Override
    public void onDataLoadedFromLocal() {
        toast(getString(R.string.load_data_success));
        mChatAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    public void onMoreDataLoadedFromLocal() {
        toast(getString(R.string.load_more_data_success));
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoMoreData() {
        toast(getString(R.string.no_more_data));
    }


    private EMMessageListenerAdapter mEMMessageListener = new EMMessageListenerAdapter() {
        @Override
        public void onMessageReceived(final List<EMMessage> list) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    toast(getString(R.string.get_new_message));
                    final EMMessage emMessage = list.get(0);
//            Log.d(TAG, "onMessageReceived: " + mUserName + " " + emMessage.getUserName() + emMessage.getBody().toString());
                   mChatPresenter.makeMessageRead(mUserName);
                    mChatAdapter.addNewMessage(emMessage);
                    smoothScrollToBottom();
                }
            });

        }
    };

    private void smoothScrollToBottom() {
        mRecyclerView.smoothScrollToPosition(mChatAdapter.getItemCount() - 1);
    }

    private void scrollToBottom() {
        mRecyclerView.scrollToPosition(mChatAdapter.getItemCount() - 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                Log.d(TAG, "onScrollStateChanged: " + firstVisibleItemPosition);
                if (firstVisibleItemPosition == 0) {
                    mChatPresenter.loadMoreDataFromServer(mUserName);
                }
            }
        }
    };
}
