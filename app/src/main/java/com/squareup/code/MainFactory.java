package com.squareup.code;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<BaseViewItem>();
        adapter = new RecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

//        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 1000);
//
//
//            }
//        });
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {

            }
        });
        url = "file:///android_asset/per.txt";
////                                url = "/sdcard/per.txt";
//                url = "http://yangleilt.iteye.com/blog/710412";
        HttpUtils.getInstance(getActivity().getApplication()).getAsynMainHttp(url, DataUnit.class);//返回根据JSON解析的对象
//        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PermissionsGrantActivity.grantPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}
////                        , new DefaultDeniedPermissionHandler(MainActivity.this) {
////                            @Override
////                            public void onPermissionsResult(String[] grantedpermissions, String[] denied_permissions) {
////                                url = "file:///android_asset/per.txt";
////                                url = "/sdcard/per.txt";
//                url = "http://yangleilt.iteye.com/blog/710412";
////                                HttpUtils.getInstance(getApplication()).getAsynMainHttp(url, DataUnit.class);//返回根据JSON解析的对象
//                HttpUtils.getInstance(getApplication()).getAsynThreadHttp(url, String.class);//返回根据JSON解析的对象
////                                HttpUtils.getInstance(getApplication()).getAsynThreadHttp(url, String.class);//返回原来的JSON
//
//
////                            }
////
////                            @Override
////                            protected void onDialogChoice(int choice) {
////                            }
////                        });
//
//            }
//        });
//        ImageView imageview = (ImageView) findViewById(R.id.imageview);
//        ImageUtils.loadImage(MainActivity.this, "https://imgsa.baidu.com/news/q%3D100/sign=79286537942f070859052e00d925b865/f7246b600c338744150658045b0fd9f9d62aa083.jpg", imageview);

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
                                    MainItemView mainItemView = new MainItemView();
                                    mainItemView.setItemData(cardUnit.getItems().get(0));
                                    list.add(mainItemView);
                                }

                            }
                        }
                        List<ItemData> itemDatas = card.getItems();
                        if (itemDatas != null) {
                            for (ItemData itemData : itemDatas) {
                                DoubleItemView mainItemView = new DoubleItemView();
                                mainItemView.setItemData(itemData);
                                list.add(mainItemView);
                            }
                        }
                    }
                    for (ItemData itemData : dataUnit.getItems()) {
                        DoubleItemView mainItemView = new DoubleItemView();
                        mainItemView.setItemData(itemData);
                        list.add(mainItemView);
                    }
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
