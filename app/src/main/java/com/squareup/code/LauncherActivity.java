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
//        Intent intent = PoxyActivity.startIntent(this, MainFactory.class.getName());
        Intent intent = PoxyActivity.startIntent(this, WebViewFactory.class.getName());
        intent.putExtra(WebViewFactory.EXTRA_NAME_URL,"https://www.baidu.com");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);


        Intent intent1 = PoxyActivity.startIntent(this, WebViewFactory.class.getName());
        intent1.putExtra(WebViewFactory.EXTRA_NAME_URL,"https://www.baidu.com");
        intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent1);
        finish();

    }


}
