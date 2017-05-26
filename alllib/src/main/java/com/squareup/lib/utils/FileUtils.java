package com.squareup.lib.utils;

import android.os.Environment;

import com.squareup.lib.BaseApplication;

import java.io.File;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class FileUtils {
    public static String getDiskCacheDir() {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (BaseApplication.application.getExternalCacheDir() == null) {
                cachePath = BaseApplication.application.getCacheDir().getPath();
            } else {
                cachePath = BaseApplication.application.getExternalCacheDir().getPath();
            }
        } else {
            cachePath = BaseApplication.application.getCacheDir().getPath();
        }
        return cachePath;
    }

    public static File getFile(String name) {
        File file = new File(getDiskCacheDir() + File.separator + name);
        if (file.exists()) {
            return file;
        } else {
            file.mkdir();
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}
