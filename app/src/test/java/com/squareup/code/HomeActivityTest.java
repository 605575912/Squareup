package com.squareup.code;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/08/10 0010.
 */
@RunWith(RobolectricTestRunner.class)
public class HomeActivityTest {
    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void isAllTranslucentStatus() throws Exception {
        ActivityController<HomeActivity> activityController = Robolectric.buildActivity(HomeActivity.class).create().start();
        HomeActivity homeActivity = activityController.get();
        Method method = (MethodUtil.getMethod(homeActivity, "isAllTranslucentStatus", new Class[0]));
        assertTrue(((boolean) method.invoke(homeActivity)));
    }

    @Test
    public void onEventMain() throws Exception {

    }

    @Test
    public void onBackPressed() throws Exception {

    }

    @Test
    public void onEventThread() throws Exception {

    }

    @Test
    public void onClick() throws Exception {

    }

    @Test
    public void onActivityResult() throws Exception {

    }

}