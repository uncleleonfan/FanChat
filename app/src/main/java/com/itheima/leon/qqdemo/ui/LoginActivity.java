package com.itheima.leon.qqdemo.ui;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.presenter.LoginPresenter;
import com.itheima.leon.qqdemo.presenter.impl.LoginPresenterImpl;
import com.itheima.leon.qqdemo.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 19:31
 * 描述：    TODO
 */
public class LoginActivity extends BaseActivity implements LoginView{
    public static final String TAG = "LoginActivity";
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.new_user)
    TextView mNewUser;

    private LoginPresenter mLoginPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick({R.id.login, R.id.new_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                String userName = mUserName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                Log.d(TAG, "onClick: " + userName + " " + password);
                mLoginPresenter.login(userName, password);
                break;
            case R.id.new_user:
                break;
        }
    }

    @Override
    public void onUserNameError() {
        mUserName.setError(getString(R.string.user_name_error));
    }

    @Override
    public void onPasswordError() {
        mPassword.setError(getString(R.string.user_password_error));
    }

    @Override
    public void onStartLogin() {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailed() {

    }
}
