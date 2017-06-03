package com.squareup.code.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.code.ItemData;
import com.squareup.code.MyApplication;
import com.squareup.code.R;
import com.squareup.code.WebViewFactory;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.activity.PoxyActivity;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class MainItemView implements BaseViewItem {
    ItemData itemData;
    Activity activity;

    public MainItemView(Activity activity) {
        this.activity = activity;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
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
        viewHolder.textView.setText(itemData.getContent());
        ImageUtils.loadImage(MyApplication.application, itemData.getImgurl(), viewHolder.imageView, R.mipmap.ic_launcher_round);
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        return new ViewHolder(linearLayout);
    }

    private class ViewHolder extends RecyclerViewHolder {
        TextView textView;
        ImageView imageView;

        private ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            textView = new TextView(linearLayout.getContext());
            imageView = new ImageView(linearLayout.getContext());
            textView.setTextColor(Color.GREEN);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 400);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            linearLayout.addView(imageView, params);
            linearLayout.addView(textView);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = PoxyActivity.startIntent(activity, WebViewFactory.class.getName());
                    intent.putExtra(WebViewFactory.EXTRA_NAME_URL, "https://www.baidu.com");
                    activity.startActivity(intent);
                }
            });
        }
    }
}
