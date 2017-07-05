package com.squareup.lib.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
    public static int getMODE_MULTI_PROCESS() {
        int MODE_MULTI_PROCESS = Context.MODE_PRIVATE;
       int mVersion = Integer.parseInt(Build.VERSION.SDK);// Integer.parseInt(Build.VERSION.SDK);
        if (mVersion >= 9) {
            Object obj = ReflectHelper.getStaticFieldValue(Context.class, "MODE_MULTI_PROCESS");
            if (obj != null && obj instanceof Integer) {
                MODE_MULTI_PROCESS = (Integer) obj;
            }
        }
        return MODE_MULTI_PROCESS;
    }



    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param imageUri
     */
    private String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
