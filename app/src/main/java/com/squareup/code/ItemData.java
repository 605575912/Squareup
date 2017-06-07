package com.squareup.code;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.squareup.lib.ImageUtils;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class ItemData extends BaseObservable {
//    public ObservableField<String> content = new ObservableField<>();
//    public ObservableInt age = new ObservableInt();
//    public ObservableBoolean isMan = new ObservableBoolean();
    /**
     * content : 最新
     * count : 110
     */
    String content;
    private String imgurl;
    private int count;

    @BindingAdapter("bind:userface")
    public static void setImage(ImageView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.mipmap.ic_launcher_round);
    }

    public String getImgurl() {
        return imgurl;
    }


    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
//        notifyPropertyChanged(BR.itemdata);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
