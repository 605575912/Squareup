package com.squareup.lib.viewfactory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liangzhenxiong on 2017/4/6 0006.
 * 主适配器
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<BaseViewItem> mdata;
    Context mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RecyclerViewAdapter(Context mContext, List<BaseViewItem> data) {
        mdata = data;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (BaseViewItem viewItem : mdata) {
            if (viewItem != null && viewItem.getViewType() == viewType) {
                RecyclerViewHolder holder = viewItem.createViewHolder(parent);
                if (holder == null) {
                    View contentview = viewItem.createView(parent);
                    if (contentview != null) {
                        return new RecyclerViewHolder(contentview);
                    }
                } else {
                    return holder;
                }
            }
        }
        return new RecyclerViewHolder(new View(mContext));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mdata.get(position).onBindViewHolder(holder, position);
    }


    @Override
    public int getItemViewType(int position) {
        return mdata.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
}
