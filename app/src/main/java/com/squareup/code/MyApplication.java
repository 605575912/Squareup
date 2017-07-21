package com.squareup.code;

import android.app.Service;
import android.os.Vibrator;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;
import com.baidu.mapapi.SDKInitializer;
import com.squareup.code.baidu.LocationService;
import com.squareup.code.utils.URLUtils;
import com.squareup.code.utils.YWCom;
import com.squareup.lib.BaseApplication;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class MyApplication extends BaseApplication {
    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        URLUtils.getInstance();


//必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(BaseApplication.getApplication(), YWCom.APP_KEY);
        }

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }
}
