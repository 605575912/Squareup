package com.squareup.code;

import android.app.Application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

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

    @After
    public void setAfter() throws Exception {
        System.out.println("结束一个案例：");
    }

    float mPhysicalCoeff = 207560.8f;
    private float mFlingFriction = 0.015f;
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)

    private double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        return mFlingFriction * mPhysicalCoeff * Math.exp(2.3582017 / 1.3582017 * l);
    }

    private double getSplineDeceleration(int velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    private double velocityfromdistance(double distance) {
        double exp = distance / (mFlingFriction * mPhysicalCoeff);
        return Math.exp(Math.log(exp) / (2.3582017 / 1.3582017)) * (mFlingFriction * mPhysicalCoeff) / INFLEXION;
    }

    @Test
    public void testplay() throws Exception {
        List list = mock(List.class);   //mock得到一个对象，也可以用@mock注入一个对象
        int velocity = 8459;
        double d = getSplineFlingDistance(velocity);//8459 ->2852.988119201405
        ;//8459 ->2852.988119201405
        System.out.println("结束一个案例：" + d);
        System.out.println("结果====：" + velocityfromdistance(d));
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
        assertEquals("Squareup", appName);
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