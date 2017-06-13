package com.squareup.code.mine;

import android.view.ViewGroup;

import com.squareup.code.R;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/13 0013.
 */

public class MineLineView extends DataBindBaseViewItem {
    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.mine_line;
    }
}
