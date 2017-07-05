package com.squareup.code.userinfo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.squareup.code.DataUnit;
import com.squareup.code.R;
import com.squareup.code.WrapContentLinearLayoutManager;
import com.squareup.code.mine.MineSpaceView;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;

/**
 * Created by Administrator on 2017/07/05 0005.
 */

public class UserinfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo_layout);
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(wrapContentLinearLayoutManager);
        recyclerview.setAdapter(adapter);
        init();
        String s;
    }

    private void init() {
        MineSpaceView mineSpaceView = new MineSpaceView();
        list.add(mineSpaceView);
        U_itemCard u_itemCard = new U_itemCard(getActivity(), "姓名：", "嘻嘻嘻");
        list.add(u_itemCard);
    }

    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getData() instanceof DataUnit) {
        }
    }
}
