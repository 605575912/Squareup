package com.squareup.code.home.tab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabsBean implements Parcelable {
    private int id;
    private String normalimgurl;
    private String pressedimgurl;
    private String jumpcontent;
    private String title;
    int index = 0;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TabsBean() {

    }

    public int getId() {
        return id;
    }

    public String getNormalimgurl() {
        return normalimgurl;
    }

    public String getPressedimgurl() {
        return pressedimgurl;
    }

    public String getJumpcontent() {
        return jumpcontent;
    }

    public String getTitle() {
        return title;
    }

    protected TabsBean(Parcel in) {
        id = in.readInt();
        index = in.readInt();
        normalimgurl = in.readString();
        pressedimgurl = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(index);
        dest.writeString(normalimgurl);
        dest.writeString(pressedimgurl);
        dest.writeString(jumpcontent);
        dest.writeString(title);
    }
}
