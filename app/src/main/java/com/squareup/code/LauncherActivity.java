package com.squareup.code;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.activity.PoxyActivity;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class LauncherActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LauncherLayoutBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.launcher_layout);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = PoxyActivity.startIntent(LauncherActivity.this, MainFactory.class.getName());
                startActivity(intent);
                finish();
            }
        };

        ItemData itemData = new ItemData();
        itemData.setContent("www.baidu.com 搜索拉翔");
        itemData.setImgurl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496407745461&di=5751fef1d0055012fc130ee2dca2beaf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a03b564da3d632f87512f695e7b4.png");
        activityMainBinding.setItemdata(itemData);
        handler.sendEmptyMessageDelayed(1, 3000);

    }


}
