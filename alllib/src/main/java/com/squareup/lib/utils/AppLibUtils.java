package com.squareup.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.morgoo.droidplugin.pm.PluginManager;
import com.squareup.lib.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class AppLibUtils {

    private static String appVersionName;
    private static int currentVersionCode;

    public static int getversionCode(Context ctx) {
        if (currentVersionCode > 0) {
            return currentVersionCode;
        }
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
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

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;

            StringBuilder buf = new StringBuilder();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = BaseApplication.application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = BaseApplication.application.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static double density;

//    public static float getdensity(Activity activity) {
//        if (density > 0) {
//            return density;
//        }
//        DisplayMetrics metric = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
//        return density;
//    }

    public static double getdensity(Activity activity) {
        if (density > 0) {
            return density;
        }
        Point point;
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        density = dm.density;
        if (Build.VERSION.SDK_INT > 13) {
            dm = activity.getResources().getDisplayMetrics();
            point = new Point();
            if (Build.VERSION.SDK_INT > 16) {
                activity.getWindowManager().getDefaultDisplay().getRealSize(point);
            } else {
                activity.getWindowManager().getDefaultDisplay().getSize(point);
            }
            double x = Math.pow(point.x / dm.xdpi, 2);
            double y = Math.pow(point.y / dm.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            density = Math.sqrt(Math.pow(point.x, 2) + Math.pow(point.y, 2)) / screenInches / DisplayMetrics.DENSITY_MEDIUM;
        }
        return density;
    }

    private static int[] defaultDisplay = null;

    /**
     * 获取当前屏幕分辨率   宽度×高度
     *
     * @param activity
     * @return
     */
    public static int[] getDefaultDisplay(Activity activity) {
        synchronized (defaultDisplay) {
            if (defaultDisplay == null) {
                DisplayMetrics metric = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
                int width = metric.widthPixels; // 屏幕宽度（像素）
                int height = metric.heightPixels; // 屏幕高度（像素）
                defaultDisplay = new int[]{width, height};
            }
        }
        return defaultDisplay;
    }

    public static void loadso(Context context, File dir, String soname) {
        //        File dir = this.getDir("lib", Activity.MODE_PRIVATE);
//        Log.i("TAG", "===dir" + dir.getAbsolutePath());
//        setload(dir, "libEasyAR.so");
//        setload(dir, "libEasyAR.so");
        File distFile = new File(dir.getAbsolutePath() + File.separator + soname);
        if (distFile.exists()) {
            //使用load方法加载内部储存的SO库
            System.load(distFile.getAbsolutePath());
            return;
        }
        if (copyFileFromAssets(context, soname, distFile.getAbsolutePath())) {
            //使用load方法加载内部储存的SO库
            System.load(distFile.getAbsolutePath());
        }
    }

    public static boolean copyFileFromAssets(Context context, String fileName, String path) {

        boolean copyIsFinish = false;

        try {

            InputStream is = context.getAssets().open(fileName);

            File file = new File(path);

            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);

            byte[] temp = new byte[1024];

            int i = 0;

            while ((i = is.read(temp)) > 0) {

                fos.write(temp, 0, i);

            }

            fos.close();

            is.close();

            copyIsFinish = true;

        } catch (IOException e) {

            e.printStackTrace();


        }

        return copyIsFinish;

    }
}
