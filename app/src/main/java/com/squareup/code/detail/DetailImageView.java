package com.squareup.code.detail;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

            LinearLayout lin = (LinearLayout) viewHolder.getViewDataBinding().getRoot().findViewById(R.id.lin);
            lin.removeAllViews();
            if (data.getLimts() != null) {
                for (String lim : data.getLimts()) {
                    LinearLayout linearLayout = new LinearLayout(activity);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    ImageView imageView = new ImageView(activity);
                    TextView textView = new TextView(activity);
                    imageView.setImageResource(R.drawable.trip_oversea_green_check_mark);
                    LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.detailimage), (int) activity.getResources().getDimension(R.dimen.detailimage));
                    imgparams.gravity = Gravity.CENTER_VERTICAL;
                    linearLayout.addView(imageView, imgparams);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    textView.setText(lim);
                    textView.setTextColor(activity.getResources().getColor(R.color.detail_green));
                    linearLayout.addView(textView, params);
                    lin.addView(linearLayout);
                }
            }

        }
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.detailimage_item;
    }
}
