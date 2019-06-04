package com.njupt.cicada.restaurantreservation.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cicada.library.utils.ToastUtils;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author guocongcong
 * @Date 2019/5/31
 * @Describe
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.ivIconUserName)
    ImageView ivIconUserName;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.ulUserName)
    View ulUserName;
    @BindView(R.id.ivIconPassword)
    ImageView ivIconPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ulPassword)
    View ulPassword;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    public void initView() {

        etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivIconUserName.setSelected(hasFocus);
                ulUserName.setSelected(hasFocus);
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivIconPassword.setSelected(hasFocus);
                ulPassword.setSelected(hasFocus);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.tvLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                startActivity(MainActivity.createIntent(mContext));
                finish();
//                String phone = etUserName.getText().toString();
//                String pass = etPassword.getText().toString();
//                if (TextUtils.isEmpty(phone)) {
//                    ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "手机号不能为空");
//                    return;
//                }
//                if (TextUtils.isEmpty(pass)) {
//                    ToastUtils.init(ToastUtils.WARNING).shortShow(mContext, "密码不能为空");
//                    return;
//                }
                break;
            default:
                break;
        }
    }

}
