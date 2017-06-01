package com.squareup.code.home;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.code.ItemData;
import com.squareup.code.MyApplication;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class MainItemView implements BaseViewItem {
    ItemData itemData;

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    @Override
    public int getViewType() {
        int hashcode = getClass().getName().hashCode();
        return hashcode;
    }

    @Override
    public View createView(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(itemData.getContent());
        ImageUtils.loadImage(MyApplication.application, itemData.getImgurl(), viewHolder.imageView);
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        return new ViewHolder(linearLayout);
    }

    private class ViewHolder extends RecyclerViewHolder {
        TextView textView;
        ImageView imageView;

        private ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            textView = new TextView(linearLayout.getContext());
            imageView = new ImageView(linearLayout.getContext());
            textView.setText("");
            textView.setTextColor(Color.GREEN);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
        }
    }
}
