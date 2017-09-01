package com.squareup.code.userinfo;

import android.os.Bundle;

import com.squareup.code.DataUnit;
import com.squareup.code.R;
import com.squareup.code.mine.MineSpaceView;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.activity.ListBaseActivity;

/**
 * Created by Administrator on 2017/07/05 0005.
 */

public class UserinfoActivity extends ListBaseActivity {

    @Override
    public int setFromLayoutID() {
        return R.layout.userinfo_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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
