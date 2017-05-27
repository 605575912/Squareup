package com.squareup.code;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.squareup.code.home.MainItemView;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.utils.AppUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.utils.ToastUtils;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(linearLayoutManager);
        List<BaseViewItem> list = new ArrayList<BaseViewItem>();
        MainItemView mainItemView = new MainItemView();
        list.add(mainItemView);
        MainItemView mainItemView1 = new MainItemView();
        list.add(mainItemView1);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this, list);
        recycler.setAdapter(adapter);

//        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                PermissionsGrantActivity.grantPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}
////                        , new DefaultDeniedPermissionHandler(MainActivity.this) {
////                            @Override
////                            public void onPermissionsResult(String[] grantedpermissions, String[] denied_permissions) {
////                                url = "file:///android_asset/per.txt";
////                                url = "/sdcard/per.txt";
//                url = "http://yangleilt.iteye.com/blog/710412";
////                                HttpUtils.getInstance(getApplication()).getAsynMainHttp(url, Per.class);//返回根据JSON解析的对象
//                HttpUtils.getInstance(getApplication()).getAsynThreadHttp(url, String.class);//返回根据JSON解析的对象
////                                HttpUtils.getInstance(getApplication()).getAsynThreadHttp(url, String.class);//返回原来的JSON
//
//
////                            }
////
////                            @Override
////                            protected void onDialogChoice(int choice) {
////                            }
////                        });
//
//            }
//        });
//        ImageView imageview = (ImageView) findViewById(R.id.imageview);
//        ImageUtils.loadImage(MainActivity.this, "https://imgsa.baidu.com/news/q%3D100/sign=79286537942f070859052e00d925b865/f7246b600c338744150658045b0fd9f9d62aa083.jpg", imageview);

    }


    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getCommand().equals(url)) {
            if (event.isSuccess()) {
                if (event.getData() instanceof Per) {
                    Per per = (Per) event.getData();
                    Toast.makeText(MainActivity.this, String.valueOf(per.getResult()), Toast.LENGTH_LONG).show();
                    LogUtil.i(per.getResult());
                } else {
                    Toast.makeText(MainActivity.this, event.getData().toString(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(MainActivity.this, "请求失败 或者JSON 错误", Toast.LENGTH_LONG).show();
                LogUtil.i("请求失败 或者JSON 错误");
            }
        }
    }

    @Override
    public void onEventThread(EventThreadObject event) {
        ToastUtils.showToast(AppUtils.getversionCode(MainActivity.this) + "");
        if (event.getCommand().equals(url)) {
            if (event.isSuccess()) {
                String per = (String) event.getData();
                LogUtil.i(per);
            } else {
                LogUtil.i("请求失败 或者JSON 错误");
            }
        }

    }
}
