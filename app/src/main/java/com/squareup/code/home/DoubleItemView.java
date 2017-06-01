package com.squareup.code.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.code.ItemData;
import com.squareup.code.MyApplication;
import com.squareup.code.R;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class DoubleItemView implements BaseViewItem {
    ItemData itemData;

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
        viewHolder.tv_.setText(itemData.getContent());
        ImageUtils.loadImage(MyApplication.application, itemData.getImgurl(), viewHolder.iv_);
    }

    ViewHolder viewHolder;

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
//        if (viewHolder != null) {
//            return viewHolder;
//        }
        View contentview = LayoutInflater.from(parent.getContext()).inflate(R.layout.douitem_layout, parent, false);
        viewHolder = new ViewHolder(contentview);
        return viewHolder;
    }

    private class ViewHolder extends RecyclerViewHolder {
        ImageView iv_;
        TextView tv_;

        private ViewHolder(View contentview) {
            super(contentview);
            tv_ = (TextView) contentview.findViewById(R.id.tv_);
            iv_ = (ImageView) contentview.findViewById(R.id.iv_);
        }
    }

}
