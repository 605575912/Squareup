package com.squareup.code.mine;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.code.account.APPAccountManager;
import com.squareup.code.userinfo.UserinfoActivity;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/13 0013.
 */

public class LoginCardView extends DataBindBaseViewItem implements View.OnClickListener {
    Activity activity;

    public LoginCardView(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.mine_login;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.user, APPAccountManager.INSTANCE.getUser());
            viewHolder.getViewDataBinding().setVariable(BR.loginview, this);
            viewHolder.getViewDataBinding().setVariable(BR.accounmanager, APPAccountManager.INSTANCE);
            APPAccountManager.INSTANCE.AutoLoginUser(activity, viewHolder);
        }
    }

    @Override
    public void onClick(View v) {
        if (APPAccountManager.INSTANCE.getLoginState() == APPAccountManager.LOGINFAILSTATE) {
            Intent intent = new Intent();
            intent.setClass(activity, LoginActivity.class);
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(activity, UserinfoActivity.class);
            activity.startActivity(intent);
        }
    }
}
