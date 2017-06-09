package com.squareup.code.home.tab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabsBean implements Parcelable {
    private int id;
    private String imgurl;
    private String jumpcontent;
    private String title;

    public TabsBean() {

    }

    protected TabsBean(Parcel in) {
        id = in.readInt();
        imgurl = in.readString();
        jumpcontent = in.readString();
        title = in.readString();
    }

    public static final Creator<TabsBean> CREATOR = new Creator<TabsBean>() {
        @Override
        public TabsBean createFromParcel(Parcel in) {
            return new TabsBean(in);
        }

        @Override
        public TabsBean[] newArray(int size) {
            return new TabsBean[size];
        }
    };

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

    public String getJumpcontent() {
        return jumpcontent;
    }

    public void setJumpcontent(String jumpcontent) {
        this.jumpcontent = jumpcontent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imgurl);
        dest.writeString(jumpcontent);
        dest.writeString(title);
    }
}
