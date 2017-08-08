package com.squareup.code;

import android.app.Service;
import android.os.Vibrator;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.code.baidu.LocationService;
import com.squareup.code.utils.URLUtils;
import com.squareup.code.utils.YWCom;
import com.squareup.lib.BaseApplication;
import com.squareup.lib.TestBaseApplication;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class TestApplication extends TestBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        URLUtils.getInstance();
        Fresco.initialize(this);
    }
}
