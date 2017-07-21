package com.squareup.lib;

import android.app.Application;

/**
 * Created by Administrator on 2017/07/21 0021.
 */

public class TestBaseApplication extends Application {
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
