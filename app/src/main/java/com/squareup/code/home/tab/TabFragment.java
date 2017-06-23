package com.squareup.code.home.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.code.Card;
import com.squareup.code.CardUnit;
import com.squareup.code.DataUnit;
import com.squareup.code.ItemData;
import com.squareup.code.MineItemData;
import com.squareup.code.R;
import com.squareup.code.WrapContentLinearLayoutManager;
import com.squareup.code.column.ColumnData;
import com.squareup.code.column.ColumnView;
import com.squareup.code.home.ChangedItemView;
import com.squareup.code.home.DoubleItemView;
import com.squareup.code.home.ItemView;
import com.squareup.code.home.PushItemView;
import com.squareup.code.home.banner.BannerModel;
import com.squareup.code.home.banner.BannerView;
import com.squareup.code.mine.LoginCardView;
import com.squareup.code.mine.MineCard;
import com.squareup.code.mine.MineCardUnit;
import com.squareup.code.mine.MineItemView;
import com.squareup.code.mine.MineLineView;
import com.squareup.code.mine.MineSpaceView;
import com.squareup.code.utils.LoadEmptyViewControl;
import com.squareup.lib.BaseFrament;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.utils.AppLibUtils;
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
    LoadEmptyViewControl loadEmptyViewControl;
    FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(R.layout.tab_layout, container, false);
        frameLayout = (FrameLayout) contentView.findViewById(R.id.container);
        loadEmptyViewControl = new LoadEmptyViewControl(getActivity());
        loadEmptyViewControl.addLoadView(frameLayout);
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



    private void addTitleView(int titletype) {
        if (titletype == 1) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_title_layout, frameLayout, false);
            view.setPadding(0, AppLibUtils.getStatusBarHeight(), 0, 0);
            frameLayout.addView(view);
        }
    }

    public void onEventMain(EventMainObject event) {
        if (tabsBean == null) {
            return;
        }

        if (event.getCommand().equals(tabsBean.getJumpcontent())) {
            if (event.getData() instanceof DataUnit) {
                loadEmptyViewControl.loadcomplete();
                DataUnit dataUnit = (DataUnit) event.getData();
                if (dataUnit.getTitletype() > 0) {
                    addTitleView(dataUnit.getTitletype());
                }
                if (dataUnit.getMinecards() != null) {
                    for (MineCard mineCard : dataUnit.getMinecards()) {
                        if (mineCard.getLogincard() != null) {
                            LoginCardView loginCardView = new LoginCardView();
                            list.add(loginCardView);
                            MineSpaceView mineSpaceView = new MineSpaceView();
                            list.add(mineSpaceView);
                        }
                        List<MineCardUnit> cardUnits = mineCard.getCardUnits();
                        if (cardUnits != null) {
                            for (MineCardUnit mineCardUnit : cardUnits) {
                                List<MineItemData> items = mineCardUnit.getItems();
                                if (items != null) {
                                    for (int i = 0; i < items.size(); i++) {
                                        MineItemView mineItemView = new MineItemView(getActivity(), items.get(i));
                                        list.add(mineItemView);
                                        if (i < items.size() - 1) {
                                            MineLineView mineLineView = new MineLineView();
                                            list.add(mineLineView);
                                        }
                                        if (i == items.size() - 1) {
                                            MineSpaceView mineSpaceView = new MineSpaceView();
                                            list.add(mineSpaceView);
                                        }
                                    }

                                }
                            }

                        }

                    }
                }
                if (dataUnit.getCards() != null) {
                    for (Card card : dataUnit.getCards()) {
                        List<BannerModel> banners = card.getBanners();
                        if (banners != null && banners.size() > 0) {
                            BannerView bannerView = new BannerView(banners);
                            list.add(bannerView);
                        }
                        List<ColumnData> columnitems = card.getColumnitems();
                        if (columnitems != null && columnitems.size() > 0) {
                            ColumnView columnView = new ColumnView(columnitems);
                            list.add(columnView);
                            MineSpaceView mineSpaceView = new MineSpaceView();
                            list.add(mineSpaceView);
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
                            MineSpaceView mineSpaceView = new MineSpaceView();
                            list.add(mineSpaceView);
                        }
                        List<ItemData> itemDatas = card.getItems();
                        if (itemDatas != null) {
                            for (ItemData itemData : itemDatas) {
                                DoubleItemView mainItemView = new DoubleItemView(getActivity());
                                mainItemView.setItemData(itemData);
                                list.add(mainItemView);
                            }
                            MineSpaceView mineSpaceView = new MineSpaceView();
                            list.add(mineSpaceView);
                        }

                    }
                }
                List<ItemData> itemDatas = dataUnit.getItems();
                if (itemDatas != null) {
                    for (ItemData itemData : dataUnit.getItems()) {
                        if (itemData.getType() == 1) {//推荐
                            PushItemView mainItemView = new PushItemView(getActivity(), itemData);
                            list.add(mainItemView);
                            LineView lineView = new LineView();
                            list.add(lineView);
                        } else {

                        }

                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                loadEmptyViewControl.loadError(event.getData().toString());
            }


        }
    }


}
