package com.squareup.code.search;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.code.R;
import com.squareup.code.databinding.SearchLayouBinding;
import com.squareup.lib.activity.BaseActivity;

/**
 * Created by Administrator on 2017/06/24 0024.
 */

public class SearchActivity extends BaseActivity {
    @Override
    public int setFromLayoutID() {
        return R.layout.search_layou;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchLayouBinding v = (SearchLayouBinding) viewDataBinding;
        v.setActivity(this);

    }

    public void onClick(View view) {
        Price_Dialog price_dialog = new Price_Dialog();
        price_dialog.show(SearchActivity.this, 100000, 1000, new Price_Dialog.PostLinstener() {
            @Override
            public void post(int price) {
                Toast.makeText(SearchActivity.this, "你出的鬼价钱" + price, Toast.LENGTH_LONG).show();
            }
        });

    }


}
