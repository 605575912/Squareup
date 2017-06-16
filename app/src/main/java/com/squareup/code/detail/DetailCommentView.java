package com.squareup.code.detail;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class DetailCommentView extends DataBindBaseViewItem {
    DetailCommentData data;
    Activity activity;


    public DetailCommentView(Activity activity, DetailCommentData data) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.data, data);
            viewHolder.getViewDataBinding().setVariable(BR.res, activity.getResources());
            ImageView iv_0 = (ImageView) viewHolder.getViewDataBinding().getRoot().findViewById(R.id.iv_0);
            ImageView iv_1 = (ImageView) viewHolder.getViewDataBinding().getRoot().findViewById(R.id.iv_1);
            ImageView iv_2 = (ImageView) viewHolder.getViewDataBinding().getRoot().findViewById(R.id.iv_2);
            ImageView iv_3 = (ImageView) viewHolder.getViewDataBinding().getRoot().findViewById(R.id.iv_3);
            ImageView iv_4 = (ImageView) viewHolder.getViewDataBinding().getRoot().findViewById(R.id.iv_4);
            if (data.getScore() > 0) {
                iv_0.setImageResource(R.drawable.ugc_addreview_star_selected_mt);
            } else {
                iv_0.setImageResource(R.drawable.ugc_addreview_star_default_mt);
            }
            if (data.getScore() > 1) {
                iv_1.setImageResource(R.drawable.ugc_addreview_star_selected_mt);
            } else {
                iv_1.setImageResource(R.drawable.ugc_addreview_star_default_mt);
            }
            if (data.getScore() > 2) {
                iv_2.setImageResource(R.drawable.ugc_addreview_star_selected_mt);
            } else {
                iv_2.setImageResource(R.drawable.ugc_addreview_star_default_mt);
            }
            if (data.getScore() > 3) {
                iv_3.setImageResource(R.drawable.ugc_addreview_star_selected_mt);
            } else {
                iv_3.setImageResource(R.drawable.ugc_addreview_star_default_mt);
            }
            if (data.getScore() > 4) {
                iv_4.setImageResource(R.drawable.ugc_addreview_star_selected_mt);
            } else {
                iv_4.setImageResource(R.drawable.ugc_addreview_star_default_mt);
            }
        }
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.detailcomment_layout;
    }
}
