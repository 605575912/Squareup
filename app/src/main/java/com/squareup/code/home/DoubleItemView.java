package com.squareup.code.home;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.code.ItemData;
import com.squareup.code.R;
import com.squareup.code.databinding.DouitemLayoutBinding;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class DoubleItemView implements BaseViewItem {
    ItemData itemData;
    Activity activity;

    @BindingAdapter("userface")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.mipmap.ic_launcher_round);
    }

    public DoubleItemView(Activity activity) {
        this.activity = activity;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    @Override
    public int getViewType() {
        return getClass().getName().hashCode();
    }

    @Override
    public View createView(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getActivityMainBinding() != null) {
            viewHolder.getActivityMainBinding().setItemdata(itemData);
            viewHolder.getActivityMainBinding().setDoubleonclick(new DoubleOnClick(activity, itemData));
        }
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        DouitemLayoutBinding activityMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.douitem_layout, parent, false);
        return new ViewHolder(activityMainBinding);
    }

    private class ViewHolder extends RecyclerViewHolder {
        DouitemLayoutBinding activityMainBinding;

        private ViewHolder(DouitemLayoutBinding activityMainBinding) {
            super(activityMainBinding.getRoot());
            this.activityMainBinding = activityMainBinding;
        }

        public DouitemLayoutBinding getActivityMainBinding() {
            return activityMainBinding;
        }
    }

}
