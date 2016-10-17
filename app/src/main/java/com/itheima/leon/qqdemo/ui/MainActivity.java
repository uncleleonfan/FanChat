package com.itheima.leon.qqdemo.ui;

import android.support.annotation.IdRes;

import com.itheima.leon.qqdemo.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {
        super.init();
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
    }

    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            switch (tabId) {
                case R.id.messages:
                    break;
                case R.id.contacts:
                    break;
                case R.id.dynamic:
                    break;
            }
        }
    };
}
