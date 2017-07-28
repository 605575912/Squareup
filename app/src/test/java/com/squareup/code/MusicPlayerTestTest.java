package com.squareup.code;

import android.app.Application;
import android.content.Intent;

import com.squareup.code.mine.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 2017/07/21 0021.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = TestApplication.class)
public class MusicPlayerTestTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("开始一个案例：");
    }

    @Test
    public void testplay() throws Exception {
        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
        assertNotNull(sampleActivity);
        assertEquals(sampleActivity.getTitle(), "Squareup");
    }

    @Test
    public void testStartActivity() {
        //按钮点击后跳转到下一个Activity
//        forwardBtn.performClick();
//        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
//        Intent expectedIntent = new Intent(sampleActivity, HomeActivity.class);
//        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
//        assertEquals(expectedIntent, actualIntent);
    }

    @Test
    public void testResources() {
        Application application = RuntimeEnvironment.application;
        String appName = application.getString(R.string.app_name);
        String activityTitle = application.getString(R.string.str_loading);
        assertEquals("LoveUT", appName);
        assertEquals("SimpleActivity",activityTitle);
    }

    @Test
    public void testLifecycle() {
//        ActivityController<LauncherActivity> activityController = Robolectric.buildActivity(LauncherActivity.class).create().start();
//        Activity activity = activityController.get();
//        TextView textview = (TextView) activity.findViewById(R.id.tv_lifecycle_value);
//        assertEquals("onCreate",textview.getText().toString());
//        activityController.resume();
//        assertEquals("onResume", textview.getText().toString());
//        activityController.destroy();
//        assertEquals("onDestroy", textview.getText().toString());
    }


}