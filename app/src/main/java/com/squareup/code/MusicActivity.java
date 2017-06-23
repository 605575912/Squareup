package com.squareup.code;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.squareup.lib.view.MindleViewPager;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

//TODO 有FixedHeader的界面fling有问题
public class MusicActivity extends Activity {

    RecyclerViewAdapter adapter;
    MindleViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);


        setupListView((RecyclerView) findViewById(R.id.listView));
    }

    private void setupListView(final RecyclerView recyclerView) {
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        final TwinklingRefreshLayout refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refresh);
        ProgressLayout headerView = new ProgressLayout(this);
        refreshLayout.setHeaderView(headerView);
//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                refreshLayout.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
//            }
//        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        View title = findViewById(R.id.title);
        refreshLayout.setTitle(title);


        viewpager = (MindleViewPager) findViewById(R.id.viewpager);
        if (viewpager != null) {
            final List<String> list = new ArrayList<>();
            list.add("1");
            viewpager.setAdapter(new MindleViewPager.LunAdapter() {
                @Override
                public View getview(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(container.getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageResource(R.drawable.trip_flight_home_train_default_banner);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    return imageView;
                }
            }, list);
        }


//        View exHeader = View.inflate(this, R.layout.header_music, null);
//        refreshLayout.addFixedExHeader(exHeader);
        refreshLayout.setOverScrollRefreshShow(false);
//        refreshLayout.setFloatRefresh(true);
        List<BaseViewItem> data = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, data);
        recyclerView.setAdapter(adapter);
//        adapter.refreshCard();

//        exHeader.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.show("fixed header clicked!");
//            }
//        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ToastUtils.showToast("item clicked!");
//            }
//        });

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.refreshCard();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.loadMoreCard();
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
    }
}
