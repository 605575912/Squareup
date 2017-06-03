package com.squareup.code;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.lib.ImageUtils;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class ItemData extends BaseObservable {
    /**
     * content : 最新
     * count : 110
     */

    private String content;
    private String imgurl;
    private int count;

    @BindingAdapter("bind:userface")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.mipmap.ic_launcher_round);
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
//        notifyPropertyChanged(BR.itemdata);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
