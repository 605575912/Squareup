package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/06/03 0003.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<ItemData> mData = new ArrayList<>();

    public MyAdapter(List<ItemData> data) {
        mData.addAll(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater
//                .from(viewGroup.getContext()), R.layout.douitem_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.douitem_layout, viewGroup));
//        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//        viewHolder.getBinding().setVariable(BR.itemdata, mData.get(i));
//        viewHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }
}
