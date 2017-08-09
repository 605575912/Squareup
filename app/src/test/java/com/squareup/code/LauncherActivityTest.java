package com.squareup.code;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.code.launcher.LauncherCache;
import com.squareup.code.launcher.LauncherMode;
import com.squareup.lib.EventMainObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Administrator on 2017/08/08 0008.
 */
@RunWith(RobolectricTestRunner.class)
public class LauncherActivityTest {
    @Test
    public void onClick() throws Exception {
        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
//        ActivityController<LauncherActivity> activityController = Robolectric.buildActivity(LauncherActivity.class).create().start();
//        Activity activity = activityController.get();
        TextView test_tv_radio = (TextView) sampleActivity.findViewById(R.id.test_tv_radio);
        test_tv_radio.performClick();


        Intent expectedIntent = new Intent(sampleActivity, HomeActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
    }

    @Test
    public void isAllTranslucentStatus() throws Exception {
        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
        Method method = (MethodUtil.getMethod(sampleActivity, "isAllTranslucentStatus", new Class[0]));
        assertTrue(((boolean) method.invoke(sampleActivity)));
    }

    @Test
    public void onEventMain() throws Exception {
        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
        LauncherCache launcherCache = new LauncherCache();
        launcherCache.getCacheData();
        EventMainObject eventMainObject = mock(EventMainObject.class);
        when(eventMainObject.getCommand()).thenReturn(launcherCache.getCommand());
        when(eventMainObject.getData()).thenReturn(new LauncherMode());
        sampleActivity.onEventMain(eventMainObject);
        verify(eventMainObject, times(1)).getCommand();
        //----------------------------------------------------------------------------
        LauncherPenster launcherPenster = mock(LauncherPenster.class);
        Field field = sampleActivity.getClass().getDeclaredField("launcherPenster");
        field.setAccessible(true);
        field.set(sampleActivity, launcherPenster);

        EventMainObject eventMainObjecta = mock(EventMainObject.class);
        when(eventMainObjecta.getCommand()).thenReturn(launcherCache.getCommand());
        when(eventMainObjecta.getData()).thenReturn(new LauncherMode());
        sampleActivity.onEventMain(eventMainObjecta);

        Field handlerfield = sampleActivity.getClass().getDeclaredField("handler");
        handlerfield.setAccessible(true);
        Handler handler = (Handler) handlerfield.get(sampleActivity);

        Field bindingfield = sampleActivity.getClass().getDeclaredField("activityMainBinding");
        bindingfield.setAccessible(true);
        LauncherLayoutBinding binding = (LauncherLayoutBinding) bindingfield.get(sampleActivity);

        verify(launcherPenster, times(1)).lanuncher(handler, binding, (LauncherMode) eventMainObjecta.getData());

        //----------------------------------------------------------------------------
        Handler mockhandler = mock(Handler.class);
        handlerfield.set(sampleActivity, mockhandler);

        when(eventMainObjecta.getCommand()).thenReturn(launcherCache.getCommand());
        when(eventMainObjecta.getData()).thenReturn(new Object());
        sampleActivity.onEventMain(eventMainObjecta);
        verify(mockhandler, times(1)).sendEmptyMessageDelayed(0, 1000);
    }
}