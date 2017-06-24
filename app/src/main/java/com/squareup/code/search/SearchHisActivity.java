package com.squareup.code.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.code.databinding.SearchHisLayoutBinding;
import com.squareup.lib.BaseActivity;

/**
 * Created by Administrator on 2017/06/24 0024.
 */

public class SearchHisActivity extends BaseActivity {
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
    }

    public void onBackClick(View view) {
        finish();
    }
}
