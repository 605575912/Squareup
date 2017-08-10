package com.squareup.code;

import android.content.Intent;

import com.squareup.code.home.tab.TabsCache;
import com.squareup.code.launcher.LauncherCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Administrator on 2017/08/08 0008.
 */
@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 21, application = TestApplication.class)
public class LauncherPensterTest {
    @Test
    public void startHome() throws Exception {
        LauncherPenster penster = new LauncherPenster();
        LauncherActivity sampleActivity = Robolectric.setupActivity(LauncherActivity.class);
        penster.startHome(sampleActivity);
        Intent expectedIntent = new Intent(sampleActivity, HomeActivity.class);
        Intent actualIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());

        LauncherActivity activity = mock(LauncherActivity.class);
        penster.startHome(activity);
        verify(activity).finish();


        LauncherActivity nulllactivity = mock(LauncherActivity.class);
        penster.startHome(null);
        verify(nulllactivity, times(0)).finish();

    }

    @Test
    public void initService() throws Exception {
        LauncherPenster penster = new LauncherPenster();
        LauncherCache launcherCache = mock(LauncherCache.class);
        penster.initService(launcherCache);
//        Method method = (MethodUtil.getMethod(penster, "workCache", new Class[]{LauncherCache.class, TabsCache.class}));
//        method.invoke(penster, null, null);
        verify(launcherCache, times(1)).getCacheData();
    }

    @Test
    public void workCache() throws Exception {
        LauncherPenster penster = new LauncherPenster();
        LauncherCache launcherCache = mock(LauncherCache.class);
        TabsCache tabsCache = mock(TabsCache.class);

        Method method = (MethodUtil.getMethod(penster, "workCache", new Class[]{LauncherCache.class, TabsCache.class}));
        method.invoke(penster, launcherCache, tabsCache);
//        penster.workCache(launcherCache, tabsCache);
        verify(tabsCache).dowlNewWorkData();
        verify(launcherCache).getCacheData();
        verify(launcherCache).dowlNewWorkData();
//--------------------------------------------------------------------
        LauncherCache nulauncherCache = mock(LauncherCache.class);
        TabsCache nulltabsCache = mock(TabsCache.class);

        method.invoke(penster, null, null);

        verify(nulltabsCache, times(0)).dowlNewWorkData();
        verify(nulauncherCache, times(0)).getCacheData();
        verify(nulauncherCache, times(0)).dowlNewWorkData();
    }


}