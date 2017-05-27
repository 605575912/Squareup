package com.squareup.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.RemoteException;
import android.widget.Toast;

import com.morgoo.droidplugin.pm.PluginManager;
import com.squareup.lib.BaseApplication;

import java.io.File;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class AppUtils {
    public static int getversionCode(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 360 插件化
     *
     * @param apkpath
     */
    public static void startOutApk(Context context, final String apkpath) {
        File apk = new File(apkpath);
        if (apk.exists() && apk.getPath().toLowerCase().endsWith(".apk")) {
            if (!PluginManager.getInstance().isConnected()) {
                Toast.makeText(context, "插件服务正在初始化，请稍后再试。。。", Toast.LENGTH_SHORT).show();
                return;
            }
            PackageManager pm = context.getPackageManager();
            final PackageInfo info = pm.getPackageArchiveInfo(apk.getPath(), 0);
            if (info != null) {
                String packageName = info.packageName;
                try {
                    if (PluginManager.getInstance().getPackageInfo(packageName, 0) != null) {
                        Intent intent = pm.getLaunchIntentForPackage(packageName);//包名
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
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



}
