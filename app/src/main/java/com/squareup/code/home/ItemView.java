package com.squareup.code.home;

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
import com.squareup.code.ItemData;
import com.squareup.code.R;
import com.squareup.code.utils.LauncherUrl;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class ItemView extends DataBindBaseViewItem implements View.OnClickListener {
    ItemData itemData;
    Activity activity;

    public ItemView(Activity activity, ItemData itemData) {
        this.itemData = itemData;
        this.activity = activity;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    @BindingAdapter("iconurl")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.mipmap.ic_launcher_round);
    }

    @BindingAdapter("background")
    public static void setImage(final View iv, String userface) {
        iv.setBackgroundColor(Color.WHITE);
        if (!TextUtils.isEmpty(userface)) {
            if (userface.startsWith("#")) {
                try {
                    iv.setBackgroundColor(Color.parseColor(userface));
                } catch (Exception ignored) {
                }
            } else {
                Glide.with(iv.getContext()).load(userface).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        iv.setBackgroundDrawable(resource);
                    }
                });
            }

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataBindBaseViewItem.ViewHolder viewHolder = (DataBindBaseViewItem.ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.itemdata, itemData);
            viewHolder.getViewDataBinding().setVariable(BR.onclick, this);
        }
    }


    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.item_layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent = LauncherUrl.launcherActivity(itemData.getJumpurl());
        if (intent != null && activity != null) {
            activity.startActivity(intent);
        }
    }
}
