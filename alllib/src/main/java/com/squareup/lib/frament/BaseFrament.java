package com.squareup.lib.frament;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;



/**
 * Created by Administrator on 2017/06/09 0009.
 */

public abstract class BaseFrament extends Fragment {
    protected View contentView;

    protected void onRefresh() {
    }

    protected void onLoadMore() {
    }

    public
    @LayoutRes
    int getFromLayoutID() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getFromLayoutID(), container, false);
        return contentView;
    }

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
