package com.squareup.code;


import com.squareup.code.utils.URLUtils;
import com.squareup.lib.BaseApplication;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        URLUtils.getInstance();
    }
}
