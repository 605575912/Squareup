package com.squareup.code.userinfo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/07/05 0005.
 */

public class U_itemCard extends DataBindBaseViewItem {
    Activity activity;
    String title;
    String content;

    public U_itemCard(Activity activity, String title, String content) {
        this.activity = activity;
        this.title = title;
        this.content = content;
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.uitem_card;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.title, title);
            viewHolder.getViewDataBinding().setVariable(BR.content, content);
            viewHolder.getViewDataBinding().setVariable(BR.itemview, this);
        }
    }

    public void onClick(View view) {
    }
}
