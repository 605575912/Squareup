package com.squareup.code.home.banner;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class BannerView extends DataBindBaseViewItem {
    List<BannerModel> banners;

    @BindingAdapter("bannerimg")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.drawable.trip_flight_home_train_default_banner);
    }

    public BannerView(List<BannerModel> banners) {
        this.banners = banners;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataBindBaseViewItem.ViewHolder viewHolder = (DataBindBaseViewItem.ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.banner, banners.get(0));
        }
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.banner_layout;
    }
}
