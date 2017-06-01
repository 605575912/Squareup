package com.squareup.code;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.lib.BaseActivity;
import com.squareup.lib.activity.PoxyActivity;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class LauncherActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = PoxyActivity.startIntent(this, MainFactory.class.getName());
        startActivity(intent);
        finish();

    }


}
