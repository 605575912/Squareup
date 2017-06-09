package com.squareup.code.utils;

import com.squareup.code.MyApplication;
import com.squareup.lib.HttpUtils;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class URLUtils {
    private UrlModel urlModel = new UrlModel();
    public static String HOST = "http://192.168.30.25:8080/";
    private static URLUtils urlUtils;

    public static synchronized URLUtils getInstance() {
        if (urlUtils == null) {
            synchronized (HttpUtils.class) {
                urlUtils = new URLUtils();
            }
        }
        return urlUtils;
    }

    private URLUtils() {
        HttpUtils.getInstance(MyApplication.application).getAsynThreadHttp("file:///android_asset/urls.txt", UrlModel.class, new HttpUtils.HttpListener() {
            @Override
            public void success(Object model, String data) {
                if (model instanceof UrlModel) {
                    urlModel = (UrlModel) model;
                }
            }

            @Override
            public void failed(Object model) {
            }
        });
    }

    public String getTabsurl() {
        return urlModel.getTabsurl();
    }

    public String getLauncherUrl() {
        return urlModel.getLauncherurl();
    }

    public String getBannersUrl() {
        return urlModel.getBannersurl();
    }
}
