package com.squareup.lib;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.android.debug.hv.ViewServer;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class BaseActivity extends FragmentActivity {
    public View titleView;
    public List<BaseViewItem> list;
    public RecyclerViewAdapter adapter;
    public List<BaseFrament> fragments;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatus(isAllTranslucentStatus());
    }

    protected Activity getActivity() {
        return this;
    }

    public void setStatus(boolean transtatus) {
        if (!isTranslucentStatus()) {
            return;
        }
        int h = transtatus ? 0 : AppLibUtils.getStatusBarHeight();
        FrameLayout frameLayout = ((FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content));
        if (frameLayout.getChildCount() > 0) {
            frameLayout.getChildAt(0).setPadding(0, h, 0, 0);
        } else {
            frameLayout.setPadding(0, h, 0, 0);
        }
    }

    public void setTitleView(View titleView) {
        this.titleView = titleView;
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatus(isAllTranslucentStatus());
        if (BuildConfig.DEBUG) {

            ViewServer.get(this).addWindow(this);
        }
    }

    public boolean NeedEventBus() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (isTranslucentStatus()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                    localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
                }
            }

        }
        if (NeedEventBus()) {
            EventBus.getDefault().register(this);
        }
        list = new ArrayList<BaseViewItem>();
        adapter = new RecyclerViewAdapter(getActivity(), list);
    }

    //是否透明状态栏
    protected boolean isTranslucentStatus() {
        return true;
    }

    //是否透明状态栏
    protected boolean isAllTranslucentStatus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventMainObject event) {
        if (fragments != null) {
            for (BaseFrament tabFragment : fragments) {
                tabFragment.onEventMain(event);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventThread(EventThreadObject event) {
        if (fragments != null) {
            for (BaseFrament tabFragment : fragments) {
                tabFragment.onEventThread(event);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            ViewServer.get(this).setFocusedWindow(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {

            ViewServer.get(this).removeWindow(this);
        }
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    /**
     * 就是说Activity类实现了Window的Callback接口。那就是看下Activity实现的onContentChanged方法。如下：
     * <p>
     * public void onContentChanged() {
     * }
     * 咦？onContentChanged是个空方法。那就说明当Activity的布局改动时，即setContentView()或者addContentView()方法执行完毕时就会调用该方法。
     * <p>
     * 所以当我们写App时，Activity的各种View的findViewById()方法等都可以放到该方法中，系统会帮忙回调。
     */
    @Override
    public void onContentChanged() {
    }
}
