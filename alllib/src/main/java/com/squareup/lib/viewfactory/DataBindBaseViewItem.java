package com.squareup.lib.viewfactory;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liangzhenxiong on 2017/4/6 0006.
 * 视图Item
 */

public class DataBindBaseViewItem implements BaseViewItem {

    @Override
    public int getViewType() {
        return getClass().hashCode();
    }

    @Override
    public View createView(ViewGroup parent) {
        return new View(parent.getContext());
    }

    public int createViewID(ViewGroup parent) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
//        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.douitem_layout, parent, false);
        int layoutid = createViewID(parent);
        View view = null;
        if (layoutid != 0) {
            try {
                view = LayoutInflater.from(parent.getContext()).inflate(layoutid, parent, false);
            } catch (Exception e) {

            }
        }
        if (view == null) {
            view = createView(parent);
        }
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);
        return new ViewHolder(viewDataBinding);
    }


    public class ViewHolder extends RecyclerViewHolder {
        ViewDataBinding viewDataBinding;

        private ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }

}
