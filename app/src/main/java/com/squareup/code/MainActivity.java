package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.code.databinding.ActivityMainBinding;
import com.squareup.code.home.tab.TabAdapter;
import com.squareup.code.home.tab.TabFragment;
import com.squareup.code.home.tab.TabModel;
import com.squareup.code.home.tab.TabsBean;
import com.squareup.code.home.tab.TabsCache;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {
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
                    if (tabsBean.getIndex() == 1) {
                        ImageUtils.loadImage(getActivity(), tabsBean.getPressedimgurl(), imageView);
                    } else {
                        ImageUtils.loadImage(getActivity(), tabsBean.getNormalimgurl(), imageView);
                    }
                    LinearLayout linearLayout = new LinearLayout(getActivity());
                    linearLayout.setTag(R.id.maintabs_id, tabsBean);
                    linearLayout.setOnClickListener(this);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    imgparams.weight = 1;
                    imgparams.gravity = Gravity.CENTER_HORIZONTAL;
                    linearLayout.addView(imageView, imgparams);
                    TextView textView = new TextView(getActivity());
                    textView.setText(tabsBean.getTitle());
                    textView.setTextSize(11);
                    textView.setTextColor(Color.BLACK);
                    LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textparams.gravity = Gravity.CENTER_HORIZONTAL;
                    linearLayout.addView(textView, textparams);
                    tabs_layout.addView(linearLayout, params);
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
        ToastUtils.showToast(AppLibUtils.getversionCode(getActivity()) + "");
//        if (event.getCommand().equals(url)) {
//            if (event.isSuccess()) {
//                String per = (String) event.getData();
//                LogUtil.i(per);
//            } else {
//                LogUtil.i("请求失败 或者JSON 错误");
//            }
//        }

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < tabs_layout.getChildCount(); i++) {
            TabsBean tabsBean = (TabsBean) tabs_layout.getChildAt(i).getTag(R.id.maintabs_id);
            if (v == tabs_layout.getChildAt(i)) {
                tabsBean.setIndex(1);
            } else {
                tabsBean.setIndex(0);
            }
            ImageView imageView = (ImageView) ((LinearLayout) tabs_layout.getChildAt(i)).getChildAt(0);
            if (tabsBean.getIndex() == 1) {
                viewPager.setCurrentItem(i);
                ImageUtils.loadImage(getActivity(), tabsBean.getPressedimgurl(), imageView);
            } else {
                ImageUtils.loadImage(getActivity(), tabsBean.getNormalimgurl(), imageView);
            }
        }
    }
}
