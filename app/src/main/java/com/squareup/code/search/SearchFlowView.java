package com.squareup.code.search;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.lib.view.flowlayout.FlowLayout;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/06/19 0019.
 */

public class SearchFlowView implements BaseViewItem {
    String[] strings;

    public SearchFlowView(Activity activity, String[] strings) {
        this.strings = strings;
    }

    @Override
    public int getViewType() {
        return getClass().getClass().hashCode();
    }

    @Override
    public View createView(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        FlowLayout flowLayout = new FlowLayout(parent.getContext());
        for (String s : strings) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(40, 0, 40, 0);
            textView.setText(s);
            textView.setBackgroundColor(Color.WHITE);
            flowLayout.addView(textView);
        }
        return new ViewHolder(flowLayout);
    }

    public class ViewHolder extends RecyclerViewHolder {

        private ViewHolder(View view) {
            super(view);
        }

    }
}
