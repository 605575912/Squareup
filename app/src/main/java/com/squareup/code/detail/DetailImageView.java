package com.squareup.code.detail;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class DetailImageView extends DataBindBaseViewItem {
    DetailImageData data;
    Activity activity;


    public DetailImageView(Activity activity, DetailImageData data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.data, data);
            viewHolder.getViewDataBinding().setVariable(BR.res, activity.getResources());
        }
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.detailimage_item;
    }
}
