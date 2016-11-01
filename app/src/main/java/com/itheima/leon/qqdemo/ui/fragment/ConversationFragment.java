package com.itheima.leon.qqdemo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.ConversationAdapter;
import com.itheima.leon.qqdemo.adpater.EMMessageListenerAdapter;
import com.itheima.leon.qqdemo.presenter.ConversationPresenter;
import com.itheima.leon.qqdemo.presenter.impl.ConversationPresenterImpl;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.itheima.leon.qqdemo.view.ConversationView;

import java.util.List;

import butterknife.BindView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:32
 * 描述：    TODO
 */
public class ConversationFragment extends BaseFragment implements ConversationView{

    public static final String TAG = "ConversationFragment";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ConversationAdapter mConversationAdapter;

    private ConversationPresenter mConversationPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_messages;
    }

    @Override
    protected void init() {
        super.init();
        mConversationPresenter = new ConversationPresenterImpl(this);
        mTitle.setText(getString(R.string.messages));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mConversationAdapter = new ConversationAdapter(getContext(), mConversationPresenter.getConversations());
        mRecyclerView.setAdapter(mConversationAdapter);

        mConversationPresenter.loadAllConversations();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);

    }

    @Override
    public void onAllConversationsLoaded() {
        toast(getString(R.string.load_conversations_success));
        mConversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mConversationAdapter.notifyDataSetChanged();
    }

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {

        @Override
        public void onMessageReceived(List<EMMessage> list) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast(getString(R.string.receive_new_message));
                    mConversationPresenter.loadAllConversations();
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
    }
}
