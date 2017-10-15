package com.squareup.lib.viewfactory;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.lib.utils.IProguard;


/**
 * Created by liangzhenxiong on 2017/4/6 0006.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements IProguard.ProtectClassAndMembers {
    public RecyclerViewHolder(View view) {
        super(view);
    }
}
