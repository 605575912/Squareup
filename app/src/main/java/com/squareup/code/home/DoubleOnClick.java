package com.squareup.code.home;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.squareup.code.ItemData;
import com.squareup.code.WebViewFactory;
import com.squareup.lib.activity.PoxyActivity;

/**
 * Created by Administrator on 2017/06/03 0003.
 */

public class DoubleOnClick {
    Activity activity;
    ItemData itemData;

    public DoubleOnClick(Activity activity, ItemData itemData) {
        this.activity = activity;
        this.itemData = itemData;
    }

    public void onclick(View view) {
        Intent intent = PoxyActivity.startIntent(activity, WebViewFactory.class.getName());
        intent.putExtra(WebViewFactory.EXTRA_NAME_URL, itemData.getImgurl());
        activity.startActivity(intent);
    }
}
