package com.squareup.code;

import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.code.baidu.LocationService;
import com.squareup.code.utils.URLUtils;
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
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }
}
