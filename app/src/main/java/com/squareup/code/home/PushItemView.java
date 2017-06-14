package com.squareup.code.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.code.BR;
import com.squareup.code.ItemData;
import com.squareup.code.R;
import com.squareup.code.utils.LauncherUrl;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class PushItemView extends DataBindBaseViewItem implements View.OnClickListener {
    ItemData itemData;
    Activity activity;

    public PushItemView(Activity activity, ItemData itemData) {
        this.itemData = itemData;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.pushitemdata, itemData);
            viewHolder.getViewDataBinding().setVariable(BR.onclick, this);
        }
    }


    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.pushitem_layout;
    }

    @Override
    public void onClick(View v) {
        Intent intent = LauncherUrl.launcherActivity(itemData.getJumpcontent());
        if (intent != null && activity != null) {
            activity.startActivity(intent);
        }
    }
}
