package com.squareup.code.search;

import android.os.Bundle;
import android.view.View;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.lib.activity.ListBaseActivity;
import com.squareup.lib.ThreadManager;


/**
 * Created by Administrator on 2017/06/24 0024.
 */

public class SearchHisActivity extends ListBaseActivity {


    @Override
    protected boolean isAllTranslucentStatus() {
        return false;
    }

    protected boolean isTranslucentStatus() {
        return false;
    }

    @Override
    public int setFromLayoutID() {
        return R.layout.search_his_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding.setVariable(BR.onclick, this);
        String[] a = new String[]{"1", "2"};
        SearchFlowView searchFlowView = new SearchFlowView(getActivity(), a);
        list.add(searchFlowView);
        ThreadManager.Companion.execute(new Runnable() {
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
