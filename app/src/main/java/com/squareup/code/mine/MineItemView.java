package com.squareup.code.mine;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.code.BR;
import com.squareup.code.MineItemData;
import com.squareup.code.R;
import com.squareup.code.utils.LauncherUrl;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class MineItemView extends DataBindBaseViewItem implements View.OnClickListener {
    MineItemData itemData;
    Activity activity;

    public MineItemView(Activity activity, MineItemData itemData) {
        this.itemData = itemData;
        this.activity = activity;
    }


    @BindingAdapter("lefticon")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.drawable.placeholder_error);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.mineitem, itemData);
//            viewHolder.getViewDataBinding().setVariable(BR.onclick, this);
        }
    }


    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.mine_item_layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent = LauncherUrl.launcherActivity(itemData.getJumpurl());
        if (intent != null && activity != null) {
            activity.startActivity(intent);
        }
    }
}
