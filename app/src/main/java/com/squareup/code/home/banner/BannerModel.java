package com.squareup.code.home.banner;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.code.R;
import com.squareup.lib.ImageUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class BannerModel {

    private List<BannersBean> banners;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class BannersBean {
        /**
         * id : 1
         * imgurl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496407745461&di=5751fef1d0055012fc130ee2dca2beaf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a03b564da3d632f87512f695e7b4.png
         * jumpcontent : 鏈€鏂�
         * starttime : 0
         */

        private int id;
        private String imgurl;
        private String jumpcontent;
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

        public String getJumpcontent() {
            return jumpcontent;
        }

        public void setJumpcontent(String jumpcontent) {
            this.jumpcontent = jumpcontent;
        }

        public int getStarttime() {
            return starttime;
        }

        public void setStarttime(int starttime) {
            this.starttime = starttime;
        }
    }
}
