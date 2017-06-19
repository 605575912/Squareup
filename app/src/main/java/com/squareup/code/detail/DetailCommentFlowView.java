package com.squareup.code.detail;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.lib.view.flowlayout.FlowLayout;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/06/19 0019.
 */

public class DetailCommentFlowView implements BaseViewItem {
    public DetailCommentFlowView(Activity activity, String[] strings) {
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
        flowLayout.setBackgroundColor(Color.GREEN);
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(40, 0, 40, 0);
        textView.setText("22222");
        textView.setBackgroundColor(Color.WHITE);
        flowLayout.addView(textView);
        TextView textView1 = new TextView(parent.getContext());
        textView1.setText("1111111111");
        flowLayout.addView(textView1);
        TextView textView2 = new TextView(parent.getContext());
        textView2.setText("1111111111");
        flowLayout.addView(textView2);
        TextView textView3 = new TextView(parent.getContext());
        textView3.setText("1111111111");
        flowLayout.addView(textView3);
        return new ViewHolder(flowLayout);
    }

    public class ViewHolder extends RecyclerViewHolder {

        private ViewHolder(View view) {
            super(view);
        }

    }
}
