package com.squareup.code.home;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.code.CardUnit;
import com.squareup.code.ItemData;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class TwoItemView implements BaseViewItem {
    CardUnit cardUnit;
    Activity activity;

    public TwoItemView(Activity activity, CardUnit cardUnit) {
        this.activity = activity;
        this.cardUnit = cardUnit;
    }

    @Override
    public int getViewType() {
        return getClass().getName().hashCode();
    }

    @Override
    public View createView(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        for (int i = 0; i < viewHolder.getItemViews().size(); i++) {
            ItemView itemView = viewHolder.getItemViews().get(i);
            itemView.onBindViewHolder(viewHolder.recyclerViewHolders.get(i), position);
        }
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        return new ViewHolder(linearLayout);
    }

    private class ViewHolder extends RecyclerViewHolder {
        List<ItemView> itemViews;
        List<RecyclerViewHolder> recyclerViewHolders;

        public List<ItemView> getItemViews() {
            return itemViews;
        }


        private ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            itemViews = new ArrayList<ItemView>();
            recyclerViewHolders = new ArrayList<RecyclerViewHolder>();
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (ItemData itemData : cardUnit.getItems()) {
                ItemView itemView = new ItemView(itemData);
                itemViews.add(itemView);
                RecyclerViewHolder recyclerViewHolder = itemView.createViewHolder(linearLayout);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                linearLayout.addView(recyclerViewHolder.itemView.getRootView(), params);
                recyclerViewHolders.add(recyclerViewHolder);
            }
        }
    }
}
