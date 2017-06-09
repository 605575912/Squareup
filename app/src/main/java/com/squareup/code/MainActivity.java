package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.squareup.code.databinding.ActivityMainBinding;
import com.squareup.code.home.banner.BannerModel;
import com.squareup.code.home.banner.BannerView;
import com.squareup.code.home.tab.TabModel;
import com.squareup.code.home.tab.TabsCache;
import com.squareup.code.utils.URLUtils;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.utils.AppUtils;
import com.squareup.lib.utils.ToastUtils;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.List;


public class MainActivity extends BaseActivity {
    //    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<BaseViewItem> list;
    RecyclerViewAdapter adapter;
    LinearLayout tabs_layout;
    TabsCache tabsCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_main);
        tabs_layout = (LinearLayout) findViewById(R.id.tabs_layout);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        list = new ArrayList<BaseViewItem>();
//        adapter = new RecyclerViewAdapter(getActivity(), list);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore(int currentPage) {
//            }
//        });
//        HttpUtils.getInstance(getActivity().getApplication()).getAsynMainHttp(URLUtils.getInstance().getBannersUrl(), BannerModel.class);//返回根据JSON解析的对象
        tabsCache = new TabsCache();
        tabsCache.getCacheData();

    }

    private void addTabs() {

//        tabs_layout.addView();
    }

    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getCommand().equals(URLUtils.getInstance().getBannersUrl())) {
            if (event.getData() instanceof BannerModel) {
                BannerModel bannerModel = (BannerModel) event.getData();
                BannerView bannerView = new BannerView(bannerModel);
                if (list == null) {
                    return;
                }
                if (list.size() > 0) {
                    BaseViewItem baseViewItem = list.get(0);
                    if (!(baseViewItem instanceof BannerView)) {
                        list.add(0, bannerView);
                        adapter.notifyItemInserted(0);
                    }
                } else {
                    list.add(0, bannerView);
                    adapter.notifyItemInserted(0);
                }

            }
        } else if (event.getCommand().equals(tabsCache.getCommand())) {
            if (event.getData() instanceof TabModel) {
                TabModel tabModel = (TabModel) event.getData();

            } else {

            }
        }
//        if (event.getCommand().equals(url)) {
//            if (event.isSuccess()) {
//                if (event.getData() instanceof DataUnit) {
//                    DataUnit dataUnit = (DataUnit) event.getData();
//                    for (Card card : dataUnit.getCards()) {
//                        List<CardUnit> cardUnits = card.getCardUnits();
//                        if (cardUnits != null) {
//                            for (CardUnit cardUnit : cardUnits) {
//                                if (cardUnit.getItems() != null && cardUnit.getItems().size() > 0) {
//                                    MainItemView mainItemView = new MainItemView(getActivity());
//                                    mainItemView.setItemData(cardUnit.getItems().get(0));
//                                    list.add(mainItemView);
//                                }
//
//                            }
//                        }
//                        List<ItemData> itemDatas = card.getItems();
//                        if (itemDatas != null) {
//                            for (ItemData itemData : itemDatas) {
//                                DoubleItemView mainItemView = new DoubleItemView(getActivity());
//                                mainItemView.setItemData(itemData);
//                                list.add(mainItemView);
//                            }
//                        }
//                    }
//                    for (ItemData itemData : dataUnit.getItems()) {
//                        DoubleItemView mainItemView = new DoubleItemView(getActivity());
//                        mainItemView.setItemData(itemData);
//                        list.add(mainItemView);
//                    }
//                    LogUtil.i("size===" + list.size());
//                } else {
//                    Toast.makeText(getActivity(), event.getData().toString(), Toast.LENGTH_LONG).show();
//                }
//
//            } else {
//                Toast.makeText(getActivity(), "请求失败 或者JSON 错误", Toast.LENGTH_LONG).show();
//                LogUtil.i("请求失败 或者JSON 错误");
//            }
//        }
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
