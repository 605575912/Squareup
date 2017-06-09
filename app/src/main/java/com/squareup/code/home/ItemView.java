package com.squareup.code.home;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.code.BR;
import com.squareup.code.ItemData;
import com.squareup.code.R;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class ItemView extends DataBindBaseViewItem {
    ItemData itemData;

    public ItemView(ItemData itemData) {
        this.itemData = itemData;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    @BindingAdapter("iconurl")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.mipmap.ic_launcher_round);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataBindBaseViewItem.ViewHolder viewHolder = (DataBindBaseViewItem.ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.itemdata, itemData);
        }
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.item_layout;
    }
}
