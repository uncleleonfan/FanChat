package com.itheima.leon.qqdemo.ui;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.leon.qqdemo.R;
import com.itheima.leon.qqdemo.presenter.RegisterPresenter;
import com.itheima.leon.qqdemo.presenter.impl.RegisterPresenterImpl;
import com.itheima.leon.qqdemo.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 22:07
 * 描述：    TODO
 */
public class RegisterActivity extends BaseActivity implements RegisterView {
    public static final String TAG = "RegisterActivity";
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.register)
    Button mRegister;

    private RegisterPresenter mRegisterPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        mRegisterPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void onStartRegister() {
        showProgress(getString(R.string.registering));
    }

    @Override
    public void onRegisterError() {
        hideProgress();
        Toast.makeText(RegisterActivity.this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        hideProgress();
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
    public void onConfirmPasswordError() {
        mConfirmPassword.setError(getString(R.string.user_password_confirm_error));
    }

    @OnClick(R.id.register)
    public void onClick() {
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();
        mRegisterPresenter.register(userName, password, confirmPassword);
    }
}
