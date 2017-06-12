package com.squareup.code;

import android.databinding.BaseObservable;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class MineItemData extends BaseObservable {
//    public ObservableField<String> content = new ObservableField<>();
//    public ObservableInt age = new ObservableInt();
//    public ObservableBoolean isMan = new ObservableBoolean();
    /**
     * content : 最新
     * count : 110
     */

    String title;
    String tagging;
    String jumpurl;

    public String getJumpurl() {
        return jumpurl;
    }

    public String getTagging() {
        return tagging;
    }

    private String imgurl;
    private String bckimgurl;
    private int count;

    public String getTitle() {
        return title;
    }

    public String getBckimgurl() {
        return bckimgurl;
    }

    public String getImgurl() {
        return imgurl;
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
