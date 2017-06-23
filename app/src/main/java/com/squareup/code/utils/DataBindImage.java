package com.squareup.code.utils;

import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.code.R;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.view.MindleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/06/14 0014.
 */

public class DataBindImage {
    @BindingAdapter("loginface")
    public static void setLogin(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.drawable.takeout_feedback_avatar_custom_default);
    }

    @BindingAdapter("launcher")
    public static void setlauncherImage(ImageView iv, String userface) {
        Drawable drawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                canvas.drawColor(Color.GREEN);
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

    @BindingAdapter("bannerimg")
    public static void setBannerImage(MindleViewPager mindleViewPager, String userface) {
        final List<String> list = new ArrayList<>();
        list.add("1");
        mindleViewPager.setAdapter(new MindleViewPager.LunAdapter() {
            @Override
            public View getview(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(R.drawable.trip_flight_home_train_default_banner);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                return imageView;
            }
        }, list);


    }

    @BindingAdapter("iconurl")
    public static void setImage(ImageView iv, String userface) {
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
                Glide.with(iv.getContext()).load(userface).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        iv.setBackgroundDrawable(resource);
                    }
                });
            }

        }

    }
}
