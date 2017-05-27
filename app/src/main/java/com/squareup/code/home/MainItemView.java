package com.squareup.code.home;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.lib.viewfactory.BaseViewItem;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class MainItemView implements BaseViewItem{
    @Override
    public int getViewType() {
        return this.hashCode();
    }

    @Override
    public View createView(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(parent.getContext());
        textView.setText("111111111111111111111111");
        textView.setTextColor(Color.GREEN);
        linearLayout.addView(textView);
        return linearLayout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder) {
    }
}
