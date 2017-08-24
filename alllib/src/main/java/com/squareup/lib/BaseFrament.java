package com.squareup.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class BaseFrament extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onEventMain(EventMainObject event) {
    }

    public void onEventThread(EventThreadObject event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
