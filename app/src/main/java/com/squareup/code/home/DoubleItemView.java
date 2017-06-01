package com.squareup.code.home;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.code.ItemData;
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
        int hashcode = getClass().getName().hashCode();
        LogUtil.i(hashcode + "============"+getClass().getName());
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
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        return new ViewHolder(linearLayout);
    }

    private class ViewHolder extends RecyclerViewHolder {
        TextView textView;

        private ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            textView = new TextView(linearLayout.getContext());
            textView.setText("");
            textView.setTextColor(Color.GREEN);
            linearLayout.addView(textView);
        }
    }
}
