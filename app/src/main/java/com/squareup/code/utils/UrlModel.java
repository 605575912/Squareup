package com.squareup.code.utils;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class UrlModel {
    String launcherurl = URLUtils.HOST + "launcher";
    String bannersurl = URLUtils.HOST + "banner";
    String tabsurl = URLUtils.HOST + "tabs";
    /**
     * items : {"id":1,"launcherurl":"http://192.168.30.25:8080/launcher/"}
     */
    private int id;

    public String getTabsurl() {
        return tabsurl;
    }

    public String getLauncherurl() {
        return launcherurl;
    }
    public String getBannersurl() {
        return bannersurl;
    }
    public int getId() {
        return id;
    }
}