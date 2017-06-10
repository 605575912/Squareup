//package com.squareup.code.home.banner;
//
//import com.squareup.code.MyApplication;
//import com.squareup.code.launcher.LauncherMode;
//import com.squareup.code.utils.URLUtils;
//import com.squareup.lib.HttpUtils;
//import com.squareup.lib.utils.FileUtils;
//
///**
// * Created by Administrator on 2017/06/09 0009.
// */
//
//public class BannerCache {
//    private String filename = "banners.txt";
//    private String command = "";
//
//    public String getCommand() {
//        return command;
//    }
//
//    public void getCacheData() {
//        command = HttpUtils.getInstance(MyApplication.application).getAsynMainHttp(FileUtils.getFile(filename), BannerModel.class);//返回根据JSON解析的对象
//    }
//
//    public void dowlNewWorkData() {
//        HttpUtils.getInstance(MyApplication.application).getAsynThreadHttp(URLUtils.getInstance().getBannersUrl(), BannerModel.class, new HttpUtils.HttpListener() {
//            @Override
//            public void success(Object model, String data) {
//                if (model instanceof BannerModel) {
//                    FileUtils.saveFile(FileUtils.getFile(filename), data);
//                }
//            }
//
//            @Override
//            public void failed(Object model) {
//
//            }
//        });//返回根据JSON解析的对象
//    }
//}
