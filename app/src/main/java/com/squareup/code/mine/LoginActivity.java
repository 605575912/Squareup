package com.squareup.code.mine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.squareup.code.R;
import com.squareup.code.account.APPAccountManager;
import com.squareup.code.databinding.LoginLayoutBinding;
import com.squareup.lib.activity.BaseActivity;

/**
 * Created by Administrator on 2017/07/03 0003.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginLayoutBinding loginLayoutBinding = DataBindingUtil.setContentView(this, R.layout.login_layout);
        loginLayoutBinding.setVariable(com.squareup.code.BR.loginactivity, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        APPAccountManager.INSTANCE.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {

    }

    public void onweixinClick(View v) {

    }

    public void onqqClick(View v) {
        APPAccountManager.INSTANCE.loginQQUser(this);

//                    tencentUtils.share(LauncherActivity.this, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                    tencentUtils.share(LauncherActivity.this, QQShare.SHARE_TO_QQ_TYPE_APP);
    }

    public void onphoneClick(View v) {

    }
}
