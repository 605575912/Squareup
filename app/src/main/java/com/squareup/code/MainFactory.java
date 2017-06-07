package com.squareup.code;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.code.home.DoubleItemView;
import com.squareup.code.home.MainItemView;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.activity.SimpleFactroy;
import com.squareup.lib.utils.AppUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.utils.ToastUtils;
import com.squareup.lib.view.EndlessRecyclerOnScrollListener;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainFactory extends SimpleFactroy {
    String url;
    //    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<BaseViewItem> list;
    RecyclerViewAdapter adapter;

    public MainFactory(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
//        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_main);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<BaseViewItem>();
        adapter = new RecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
            }
        });
        url = "file:///android_asset/per.txt";
        url = "http://192.168.30.25/server";
//                url = "http://yangleilt.iteye.com/blog/710412";
        HttpUtils.getInstance(getActivity().getApplication()).getAsynMainHttp(url, DataUnit.class);//返回根据JSON解析的对象

//        ImageUtils.loadImage(getActivity(),"",imageview);

    }

    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getCommand().equals(url)) {
            if (event.isSuccess()) {
                if (event.getData() instanceof DataUnit) {
                    DataUnit dataUnit = (DataUnit) event.getData();
                    for (Card card : dataUnit.getCards()) {
                        List<CardUnit> cardUnits = card.getCardUnits();
                        if (cardUnits != null) {
                            for (CardUnit cardUnit : cardUnits) {
                                if (cardUnit.getItems() != null && cardUnit.getItems().size() > 0) {
                                    MainItemView mainItemView = new MainItemView(getActivity());
                                    mainItemView.setItemData(cardUnit.getItems().get(0));
                                    list.add(mainItemView);
                                }

                            }
                        }
                        List<ItemData> itemDatas = card.getItems();
                        if (itemDatas != null) {
                            for (ItemData itemData : itemDatas) {
                                DoubleItemView mainItemView = new DoubleItemView(getActivity());
                                mainItemView.setItemData(itemData);
                                list.add(mainItemView);
                            }
                        }
                    }
                    for (ItemData itemData : dataUnit.getItems()) {
                        DoubleItemView mainItemView = new DoubleItemView(getActivity());
                        mainItemView.setItemData(itemData);
                        list.add(mainItemView);
                    }
                    LogUtil.i("size===" + list.size());
                } else {
                    Toast.makeText(getActivity(), event.getData().toString(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), "请求失败 或者JSON 错误", Toast.LENGTH_LONG).show();
                LogUtil.i("请求失败 或者JSON 错误");
            }
        }
    }

    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onEventThread(EventThreadObject event) {
        ToastUtils.showToast(AppUtils.getversionCode(getActivity()) + "");
        if (event.getCommand().equals(url)) {
            if (event.isSuccess()) {
                String per = (String) event.getData();
                LogUtil.i(per);
            } else {
                LogUtil.i("请求失败 或者JSON 错误");
            }
        }

    }
}
