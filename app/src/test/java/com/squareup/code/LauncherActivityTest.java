package com.squareup.code;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

/**
 * Created by Administrator on 2017/08/08 0008.
 */
@RunWith(RobolectricTestRunner.class)
public class LauncherActivityTest {


    @Test
    public void onClick() throws Exception {
        ActivityController<LauncherActivity> activityController = Robolectric.buildActivity(LauncherActivity.class).create().start();
        Activity activity = activityController.get();
//        TextView textview = (TextView) activity.findViewById(R.id.tv_lifecycle_value);
    }

}