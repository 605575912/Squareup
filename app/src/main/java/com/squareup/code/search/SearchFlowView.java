package com.squareup.code.search;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.code.R;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.view.flowlayout.FlowLayout;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/06/19 0019.
 */

public class SearchFlowView implements BaseViewItem {
    String[] strings;
    Activity activity;

    public SearchFlowView(Activity activity, String[] strings) {
        this.strings = strings;
        this.activity = activity;
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
        ViewHolder viewHolder = (ViewHolder) holder;
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        FlowLayout flowLayout = new FlowLayout(parent.getContext());
        for (String s : strings) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(((int) AppLibUtils.getdensity(activity) * 10), 0, ((int) AppLibUtils.getdensity(activity) * 10), 0);
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
