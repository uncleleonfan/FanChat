package com.itheima.leon.qqdemo.ui.fragment;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.adpater.EMCallBackAdapter;
import com.itheima.leon.qqdemo.ui.activity.LoginActivity;
import com.itheima.leon.qqdemo.utils.ThreadUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:39
 * 描述：    TODO
 */
public class DynamicFragment extends BaseFragment{
    public static final String TAG = "DynamicFragment";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.back)
    ImageView mBack;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void init() {
        super.init();
        String logout = String.format(getString(R.string.logout), EMClient.getInstance().getCurrentUser());
        mLogout.setText(logout);
        mTitle.setText(getString(R.string.dynamic));

    }

    @OnClick(R.id.logout)
    public void onClick() {
        showProgress(getString(R.string.logouting));
        EMClient.getInstance().logout(true, mEMCallBackAdapter);
    }


    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                    toast(getString(R.string.logout_success));
                    startActivity(LoginActivity.class, true);
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                    toast(getString(R.string.logout_failed));
                }
            });
        }
    };
}
