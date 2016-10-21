package com.itheima.leon.qqdemo.ui.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.EMMessageListenerAdapter;
import com.itheima.leon.qqdemo.factory.FragmentFactory;
import com.itheima.leon.qqdemo.utils.ThreadUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    private FragmentManager mFragmentManager;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {
        super.init();
        mFragmentManager = getSupportFragmentManager();
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
    }

    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            switch (tabId) {
                case R.id.messages:
                    fragmentTransaction.replace(R.id.fragment_container, FragmentFactory.getInstance().getMessageFragment()).commit();
                    break;
                case R.id.contacts:
                    fragmentTransaction.replace(R.id.fragment_container, FragmentFactory.getInstance().getContactFragment()).commit();
                    break;
                case R.id.dynamic:
                    fragmentTransaction.replace(R.id.fragment_container, FragmentFactory.getInstance().getDynamicFragment()).commit();
                    break;
            }
        }
    };

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {

        //该回调在子线程中调用
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            updateUnreadCount();
        }
    };

    private void updateUnreadCount() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BottomBarTab bottomBar = mBottomBar.getTabWithId(R.id.messages);
                int count = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                bottomBar.setBadgeCount(count);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUnreadCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
