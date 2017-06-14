package com.squareup.code.home.tab;

import android.view.ViewGroup;

import com.squareup.code.R;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/13 0013.
 */

public class LineView extends DataBindBaseViewItem {
    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.line;
    }
}
