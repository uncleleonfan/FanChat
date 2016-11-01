package com.itheima.leon.qqdemo.ui.activity;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.presenter.SplashPresenter;
import com.itheima.leon.qqdemo.presenter.impl.SplashPresenterImpl;
import com.itheima.leon.qqdemo.view.SplashView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/15 22:11
 * 描述：    TODO
 */
public class SplashActivity extends BaseActivity implements SplashView {
    public static final String TAG = "SplashActivity";

    private static final int DELAY = 2000;

    private SplashPresenter mSplashPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        super.init();
        mSplashPresenter = new SplashPresenterImpl(this);
        mSplashPresenter.checkLoginStatus();
    }

    @Override
    public void onNotLogin() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
            }
        }, DELAY);
    }

    @Override
    public void onLoggedIn() {
        startActivity(MainActivity.class);
    }
}
