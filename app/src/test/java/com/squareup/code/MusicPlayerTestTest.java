package com.squareup.code;

import com.squareup.lib.TestBaseApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/07/21 0021.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21,application = TestApplication.class)
public class MusicPlayerTestTest {
    @Before
    public void setUp() throws Exception {
        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
        assertNotNull(sampleActivity);
        assertEquals(sampleActivity.getTitle(), "Squareup");

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
//        Intent expectedIntent = new Intent(sampleActivity, LoginActivity.class);
//        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
//        assertEquals(expectedIntent, actualIntent);
    }

    @Test
    public void testResources() {
//        Application application = RuntimeEnvironment.application;
//        String appName = application.getString(R.string.app_name);
//        String activityTitle = application.getString(R.string.title_activity_simple);
//        assertEquals("LoveUT", appName);
//        assertEquals("SimpleActivity",activityTitle);
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