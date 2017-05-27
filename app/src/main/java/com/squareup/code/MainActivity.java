package com.squareup.code;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.morgoo.droidplugin.pm.PluginManager;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.utils.AppUtils;
import com.squareup.lib.utils.FileUtils;
import com.squareup.lib.utils.LogUtil;

import java.io.File;


public class MainActivity extends BaseActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PermissionsGrantActivity.grantPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}
//                        , new DefaultDeniedPermissionHandler(MainActivity.this) {
//                            @Override
//                            public void onPermissionsResult(String[] grantedpermissions, String[] denied_permissions) {
//                                url = "file:///android_asset/per.txt";
//                                url = "/sdcard/per.txt";
//                File file = FileUtils.getFile("/mnt/sdcard/GooglePlay_1.1.0.apk");
                startapk("/sdcard/app-debug.apk");
                url = "http://yangleilt.iteye.com/blog/710412";
//                                HttpUtils.getInstance(getApplication()).getAsynMainHttp(url, Per.class);//返回根据JSON解析的对象
                HttpUtils.getInstance(getApplication()).getAsynMainHttp(url, String.class);//返回根据JSON解析的对象
//                                HttpUtils.getInstance(getApplication()).getAsynThreadHttp(url, String.class);//返回原来的JSON
                Toast.makeText(MainActivity.this, AppUtils.getversionCode(MainActivity.this) + "", Toast.LENGTH_LONG).show();

//                            }
//
//                            @Override
//                            protected void onDialogChoice(int choice) {
//                            }
//                        });

            }
        });
        ImageView imageview = (ImageView) findViewById(R.id.imageview);
        ImageUtils.loadImage(MainActivity.this, "https://imgsa.baidu.com/news/q%3D100/sign=79286537942f070859052e00d925b865/f7246b600c338744150658045b0fd9f9d62aa083.jpg", imageview);

    }

    /**
     * 360 插件化
     *
     * @param apkpath
     */
    protected void startapk(final String apkpath) {
        File apk = new File(apkpath);
        if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
            if (!PluginManager.getInstance().isConnected()) {
                Toast.makeText(MainActivity.this, "插件服务正在初始化，请稍后再试。。。", Toast.LENGTH_SHORT).show();
                return;
            }
            PackageManager pm = getPackageManager();
            final PackageInfo info = pm.getPackageArchiveInfo(apk.getPath(), 0);
            if (info != null) {
                String packageName = info.packageName;
                try {
                    if (PluginManager.getInstance().getPackageInfo(packageName, 0) != null) {
                        Intent intent = pm.getLaunchIntentForPackage(packageName);//包名
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    int re = PluginManager.getInstance().installPackage(apkpath, 0);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }

                            }
                        }.start();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

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
                    FileUtils.saveFile("cache.txt", event.getData().toString());
                    Toast.makeText(MainActivity.this, event.getData().toString(), Toast.LENGTH_LONG).show();
                    LogUtil.i(FileUtils.readFile("cache.txt"));
                }

            } else {
                Toast.makeText(MainActivity.this, "请求失败 或者JSON 错误", Toast.LENGTH_LONG).show();
                LogUtil.i("请求失败 或者JSON 错误");
            }
        }
    }

    @Override
    public void onEventThread(EventThreadObject event) {
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
