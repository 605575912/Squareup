package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.code.databinding.ActivityMainBinding;
import com.squareup.code.home.tab.TabAdapter;
import com.squareup.code.home.tab.TabFragment;
import com.squareup.code.home.tab.TabModel;
import com.squareup.code.home.tab.TabsBean;
import com.squareup.code.home.tab.TabsCache;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.utils.AppUtils;
import com.squareup.lib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    //    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayout tabs_layout;
    TabsCache tabsCache;
    ViewPager viewPager;
    TabAdapter tabAdapter;
    List<TabFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_main);
        tabs_layout = (LinearLayout) findViewById(R.id.tabs_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragments = new ArrayList<TabFragment>();
        tabAdapter = new TabAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabAdapter);

        tabsCache = new TabsCache();
        tabsCache.getCacheData();
    }

    private void addTabs() {

//        tabs_layout.addView();
    }

    @Override
    public void onEventMain(EventMainObject event) {
        for (TabFragment tabFragment : fragments) {
            tabFragment.onEventMain(event);
        }
        if (event.getCommand().equals(tabsCache.getCommand())) {
            if (event.getData() instanceof TabModel) {
                TabModel tabModel = (TabModel) event.getData();
                tabs_layout.setBackgroundColor(Color.parseColor(tabModel.getColor()));
                for (TabsBean tabsBean : tabModel.getTabs()) {
                    ImageView imageView = new ImageView(getActivity());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.weight = 1;
                    tabs_layout.addView(imageView, params);
                    TabFragment tabFragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("TabsBean", tabsBean);
                    tabFragment.setArguments(bundle);
                    fragments.add(tabFragment);
                    tabAdapter.notifyDataSetChanged();
                }
                viewPager.setOffscreenPageLimit(fragments.size());
            } else {

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onEventThread(EventThreadObject event) {
        ToastUtils.showToast(AppUtils.getversionCode(getActivity()) + "");
//        if (event.getCommand().equals(url)) {
//            if (event.isSuccess()) {
//                String per = (String) event.getData();
//                LogUtil.i(per);
//            } else {
//                LogUtil.i("请求失败 或者JSON 错误");
//            }
//        }

    }
}
