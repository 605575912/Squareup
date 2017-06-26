package com.squareup.code.launcher;

import com.squareup.code.MyApplication;
import com.squareup.code.utils.URLUtils;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.utils.FileUtils;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class LauncherCache {
    private String filename = "launcher.txt";
    private String command = "";

    public String getCommand() {
        return command;
    }

    public void getCacheData() {
        command = HttpUtils.INSTANCE.getAsynMainHttp(FileUtils.getFile(filename), LauncherMode.class);//返回根据JSON解析的对象
    }

    public void dowlNewWorkData() {
        HttpUtils.INSTANCE.getAsynThreadHttp(URLUtils.getInstance().getLauncherUrl(), LauncherMode.class, new HttpUtils.HttpListener() {
            @Override
            public void success(Object model, String data) {
                if (model instanceof LauncherMode) {
                    FileUtils.saveFile(FileUtils.getFile(filename), data);
                }
            }

            @Override
            public void failed(Object model) {

            }
        });//返回根据JSON解析的对象
    }
}
