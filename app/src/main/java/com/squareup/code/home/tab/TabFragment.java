package com.squareup.code.home.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.code.Card;
import com.squareup.code.CardUnit;
import com.squareup.code.DataUnit;
import com.squareup.code.ItemData;
import com.squareup.code.R;
import com.squareup.code.WrapContentLinearLayoutManager;
import com.squareup.code.home.ChangedItemView;
import com.squareup.code.home.DoubleItemView;
import com.squareup.code.home.ItemView;
import com.squareup.code.home.banner.BannerModel;
import com.squareup.code.home.banner.BannerView;
import com.squareup.lib.BaseFrament;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.view.EndlessRecyclerOnScrollListener;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabFragment extends BaseFrament {
    RecyclerView recyclerView;
    List<BaseViewItem> list;
    RecyclerViewAdapter adapter;
    View contentView;
    TabsBean tabsBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(R.layout.tab_layout, container, false);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler);
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        list = new ArrayList<BaseViewItem>();
        adapter = new RecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
            }
        });
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabsBean = getArguments().getParcelable("TabsBean");
        if (tabsBean != null) {
            HttpUtils.getInstance(getActivity().getApplication()).getAsynMainHttp(tabsBean.getJumpcontent(), DataUnit.class);//返回根据JSON解析的对象
        } else {
            tabsBean = new TabsBean();
        }
    }

    public void onEventMain(EventMainObject event) {
        if (event.getCommand().equals(tabsBean.getJumpcontent())) {
            if (event.isSuccess()) {
                if (event.getData() instanceof DataUnit) {
                    DataUnit dataUnit = (DataUnit) event.getData();

                    for (Card card : dataUnit.getCards()) {
                        List<BannerModel> banners = card.getBanners();
                        if (banners != null && banners.size() > 0) {
                            BannerView bannerView = new BannerView(banners);
                            list.add(bannerView);
                        }
                        List<CardUnit> cardUnits = card.getCardUnits();
                        if (cardUnits != null) {
                            for (CardUnit cardUnit : cardUnits) {
                                List<ItemData> itemDatas = cardUnit.getItems();
                                if (itemDatas == null || itemDatas.size() == 0) {
                                    continue;
                                }
                                if (itemDatas.size() == 1) {
                                    ItemView baseViewItem = new ItemView(getActivity(), itemDatas.get(0));
                                    list.add(baseViewItem);
                                    continue;
                                }
                                ChangedItemView baseViewItem = new ChangedItemView(getActivity(), itemDatas);
                                list.add(baseViewItem);

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
                    List<ItemData> itemDatas = dataUnit.getItems();
                    if (itemDatas!=null){
                        for (ItemData itemData : dataUnit.getItems()) {
                            DoubleItemView mainItemView = new DoubleItemView(getActivity());
                            mainItemView.setItemData(itemData);
                            list.add(mainItemView);
                        }
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), event.getData().toString(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), "请求失败 或者JSON 错误", Toast.LENGTH_LONG).show();
                LogUtil.i("请求失败 或者JSON 错误");
            }
        }
    }


}
