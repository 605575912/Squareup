package com.squareup.code.utils;

import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.code.R;
import com.squareup.code.home.banner.BannerModel;
import com.squareup.code.views.RadioTextView;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.view.MindleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/06/14 0014.
 */

public class DataBindImage {
    @BindingAdapter("loginface")
    public static void setLogin(SimpleDraweeView iv, String userface) {
        ImageUtils.loadRoundImage(iv.getContext(), userface, iv, R.drawable.takeout_feedback_avatar_custom_default);
    }

    @BindingAdapter("launcher")
    public static void setlauncherImage(SimpleDraweeView iv, String userface) {
        Drawable drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSPARENT;
            }
        };
        ImageUtils.loadImage(iv.getContext(), userface, iv, drawable);
    }

    @BindingAdapter("banners")
    public static void setBannerImage(MindleViewPager mindleViewPager, final List<BannerModel> bannerModels) {
        mindleViewPager.setAdapter(new MindleViewPager.LunAdapter() {
            @Override
            public View getview(ViewGroup container, int position) {
                SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageUtils.loadImage(bannerModels.get(position).getImgurl(), imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                return imageView;
            }
        }, bannerModels, R.drawable.mtadvert_indicator_selected, R.drawable.mtadvert_indicator_normal);


    }

    @BindingAdapter("iconurl")
    public static void setImage(SimpleDraweeView iv, String userface) {
//        userface = "http://192.168.30.13:8080/img/out.gif";
//        Glide.with(iv.getContext()).asGif().load(userface).into(iv);
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.drawable.placeholder_error);
    }

    @BindingAdapter("background")
    public static void setBackgroundImage(final View iv, String userface) {
        iv.setBackgroundColor(Color.WHITE);
        if (!TextUtils.isEmpty(userface)) {
            if (userface.startsWith("#")) {
                try {
                    iv.setBackgroundColor(Color.parseColor(userface));
                } catch (Exception ignored) {
                }
            } else {
//                Glide.with(iv.getContext()).load(userface).into(new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                        iv.setBackgroundDrawable(resource);
//                    }
//                });
            }

        }

    }

    @BindingAdapter("launchertime")
    public static void setBannerTime(RadioTextView radioTextView, int time) {
        if (Build.VERSION.SDK_INT >= 19) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) radioTextView.getLayoutParams();
            params.topMargin = AppLibUtils.getStatusBarHeight();
        }
        radioTextView.setTime(time);
    }


}
