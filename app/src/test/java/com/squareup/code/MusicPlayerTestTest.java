package com.squareup.code;

import android.app.Application;
import android.os.Handler;

import com.squareup.lib.utils.FileSplitter;
import com.squareup.lib.utils.FileUnifier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Administrator on 2017/07/21 0021.
 */
public class MusicPlayerTestTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("开始一个案例：");

    }

    @After
    public void setAfter() throws Exception {
        System.out.println("结束一个案例：");
    }


    @Test
    public void testList() throws Exception {
        //mock creation
        List mockedList = mock(List.class);
//        Handler handler = new Handler();
        long time = 1506702456;

//        simpleDateFormat.format(new Date());


        String lsSrcFile = "D:\\奔腾年代.rmvb";
        String lsDstDir = "D:\\parts";
        String lsDstFile = "D:\\parts\\dst.rmvb";
        File loDstFile = new File(lsDstDir);
        if (!loDstFile.exists()) {
            loDstFile.mkdir();
        }

        long liStartTime = System.currentTimeMillis();
        FileSplitter loSplitter = new FileSplitter(lsSrcFile, lsDstDir, "test_cut");
        String[] laPartFiles = loSplitter.start();
        while (loSplitter.isBusy()) {
            Thread.sleep(200);
        }
        FileUnifier loUnifier = new FileUnifier(laPartFiles, lsDstFile);
        loUnifier.start();
        while (loUnifier.isBusy()) {
            Thread.sleep(200);
        }
        System.out.println("done, spend " + (System.currentTimeMillis() - liStartTime) + " milsecs");


        System.out.println("结束一个案例：" + new Date().getTime());
// using mock object
//        mockedList.add("one");
//        mockedList.clear();
//        mockedList.add("3"); // no verify? OK
//
//// verification
//        verify(mockedList).add("one");
//        verify(mockedList).clear();
////        verify(mockedList).add("2"); // this will throw an exception
//        System.out.println("结案例：");
//
//        when(mockedList.get(0)).thenReturn("first");
//        System.out.println(mockedList.get(0));

        when(mockedList.get(anyInt())).thenReturn("element");

// following prints "element"
        System.out.println(mockedList.get(anyInt()));

// you can also verify using an argument matcher
        verify(mockedList).get(anyInt());
    }


    @Test
    public void testplay() throws Exception {
        List list = mock(List.class);   //mock得到一个对象，也可以用@mock注入一个对象
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
//        String appName = application.getString(R.string.app_name);
//        String activityTitle = application.getString(R.string.str_loading);
//        assertEquals("Squareup", appName);
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