package com.squareup.code;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.code.utils.URLUtils;
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
