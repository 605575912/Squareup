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
        for (ItemView itemView : viewHolder.getItemViews()) {
//            itemView.setItemData(cardUnit.getItems().get(0));
            itemView.onBindViewHolder(viewHolder.recyclerViewHolder, position);
        }
//        viewHolder.textView.setText(itemData.getContent());
//        ImageUtils.loadImage(MyApplication.application, itemData.getImgurl(), viewHolder.imageView, R.mipmap.ic_launcher_round);
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        return new ViewHolder(linearLayout);
    }

    private class ViewHolder extends RecyclerViewHolder {
        List<ItemView> itemViews;
        RecyclerViewHolder recyclerViewHolder;

        public List<ItemView> getItemViews() {
            return itemViews;
        }

        public RecyclerViewHolder getRecyclerViewHolder() {
            return recyclerViewHolder;
        }

        private ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            itemViews = new ArrayList<ItemView>();
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (ItemData itemData : cardUnit.getItems()) {
                ItemView itemView = new ItemView(itemData);
                itemViews.add(itemView);
                recyclerViewHolder = itemView.createViewHolder(linearLayout);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                linearLayout.addView(recyclerViewHolder.itemView.getRootView(), params);
            }
//            textView = new TextView(linearLayout.getContext());
//            imageView = new ImageView(linearLayout.getContext());
//            textView.setTextColor(Color.GREEN);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 400);
//            params.gravity = Gravity.CENTER_HORIZONTAL;
//            linearLayout.addView(imageView, params);
//            linearLayout.addView(textView);
//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = PoxyActivity.startIntent(activity, WebViewFactory.class.getName());
//                    intent.putExtra(WebViewFactory.EXTRA_NAME_URL, "https://www.baidu.com");
//                    activity.startActivity(intent);
//                }
//            });
        }
    }
}
