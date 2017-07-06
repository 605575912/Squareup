package com.squareup.lib.utils;

import android.Manifest;
import android.os.Environment;
import android.text.TextUtils;

import com.squareup.lib.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class FileUtils {
    /**
     * 不需要权限
     *
     * @return
     */
    public static String getDiskCacheDir() {
        String cachePath;
        if (!PermissionUtil.selfPermissionGranted(BaseApplication.application, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return BaseApplication.application.getCacheDir().getPath();
        }
        if (!PermissionUtil.selfPermissionGranted(BaseApplication.application, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return BaseApplication.application.getCacheDir().getPath();
        }
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

    /**
     * 非录下的 文件 不需要权限的
     *
     * @param name
     * @return
     */

    public static File getFile(String name) {
        File file = new File(getDiskCacheDir() + File.separator + name);
        if (file.exists()) {
            return file;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file.exists()) {
                return file;
            }
        }
        return file;
    }

    public static String readFile(String name) {
        File file = getFile(name);
        return readFile(file);
    }

    public static void saveFile(String name, String data) {
        File file = getFile(name);
        saveFile(file, data);
    }

    public static String readFile(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] b = new byte[is.available()];
            is.read(b);
            String data = new String(b);

            if (!TextUtils.isEmpty(data)) {
                return data;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 非目录下的 文件 需要权限的
     *
     * @param file
     * @param data
     */
    public static void saveFile(File file, String data) {
        if (file == null) {
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
