package com.squareup.code.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.code.WrapContentLinearLayoutManager;
import com.squareup.code.databinding.SearchHisLayoutBinding;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.ThreadManager;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/06/24 0024.
 */

public class SearchHisActivity extends BaseActivity {
    ArrayList<BaseViewItem> list;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    @Override
    protected boolean isAllTranslucentStatus() {
        return false;
    }

    protected boolean isTranslucentStatus() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchHisLayoutBinding searchHisLayoutBinding = DataBindingUtil.setContentView(this, R.layout.search_his_layout);
        searchHisLayoutBinding.setVariable(BR.onclick, this);
        recyclerView = (RecyclerView) searchHisLayoutBinding.getRoot().findViewById(R.id.recycler);
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
        list = new ArrayList<BaseViewItem>();
        adapter = new RecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        String[] a = new String[]{"1", "2"};
        SearchFlowView searchFlowView = new SearchFlowView(getActivity(), a);
        list.add(searchFlowView);
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    public void onBackClick(View view) {
        finish();
    }

    public void onSearchClick(View view) {
    }
}
