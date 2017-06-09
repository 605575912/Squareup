package com.squareup.code.launcher;

import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.lib.ImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/06/08 0008.
 */

public class LauncherMode {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    @BindingAdapter("launcher")
    public static void setImage(ImageView iv, String userface) {
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

    public static class ItemsBean {
        /**
         * id : 1
         * imgurl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496407745461&di=5751fef1d0055012fc130ee2dca2beaf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a03b564da3d632f87512f695e7b4.png
         * content : 最新
         * starttime : 0
         */

        private int id;
        private String imgurl;
        private String content;
        private int starttime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStarttime() {
            return starttime;
        }

        public void setStarttime(int starttime) {
            this.starttime = starttime;
        }
    }
}
