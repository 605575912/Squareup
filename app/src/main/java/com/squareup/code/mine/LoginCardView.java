package com.squareup.code.mine;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.code.account.AccountManager;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.utils.ToastUtils;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/13 0013.
 */

public class LoginCardView extends DataBindBaseViewItem implements View.OnClickListener {
    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.mine_login;
    }

    @BindingAdapter("loginface")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.drawable.takeout_feedback_avatar_custom_default);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.user, AccountManager.getInstance().getUser());
            viewHolder.getViewDataBinding().setVariable(BR.loginview, this);
            viewHolder.getViewDataBinding().setVariable(BR.accounmanager, AccountManager.getInstance());
        }
    }

    @Override
    public void onClick(View v) {
        ToastUtils.showToast("===");
    }
}
