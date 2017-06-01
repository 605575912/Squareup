package com.squareup.lib;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class BaseActivity extends Activity {
    public View titleView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatus();
    }

    private void setStatus() {
        if (isTranslucentStatus()) {
//            titleBar.setFitsSystemWindows(true);
            if (titleView == null) {
                FrameLayout frameLayout = ((FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content));
                if (frameLayout.getChildCount() > 0) {
                    frameLayout.getChildAt(0).setPadding(0, getStatusBarHeight(), 0, 0);
                } else {
                    frameLayout.setPadding(0, getStatusBarHeight(), 0, 0);
                }
            } else {
                titleView.setPadding(0, getStatusBarHeight(), 0, 0);
            }

        }
    }

    public void setTitleView(View titleView) {
        this.titleView = titleView;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatus();
    }

    public boolean NeedEventBus() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (isTranslucentStatus()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        }
        if (NeedEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    //是否透明状态栏
    protected boolean isTranslucentStatus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventMainObject event) {
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventThread(EventThreadObject event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
