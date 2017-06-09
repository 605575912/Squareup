package com.squareup.code.home.tab;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabModel {

    private List<TabsBean> tabs;

    public List<TabsBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabsBean> tabs) {
        this.tabs = tabs;
    }

    public static class TabsBean {
        /**
         * id : 1
         * imgurl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496407745461&di=5751fef1d0055012fc130ee2dca2beaf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a03b564da3d632f87512f695e7b4.png
         * jumpcontent :
         * title : 1
         */

        private int id;
        private String imgurl;
        private String jumpcontent;
        private String title;

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
    }
}
