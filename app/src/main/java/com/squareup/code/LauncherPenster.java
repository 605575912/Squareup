package com.squareup.code;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.squareup.code.home.tab.TabsCache;
import com.squareup.code.launcher.LauncherCache;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2017/08/08 0008.
 */

public class LauncherPenster {
    public void startHome(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void workCache(LauncherCache launcherCache, TabsCache tabsCache) {
        if (tabsCache != null) {
            tabsCache.dowlNewWorkData();
        }
        if (launcherCache == null) {
            return;
        }
        launcherCache.getCacheData();
        launcherCache.dowlNewWorkData();
    }

    /**
     * 从本地获取广告图片
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    Bitmap getimage(String path, int width, int height) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = false;
            newOpts.inSampleSize = 1;
            Bitmap tempbitmap;
            try {
                tempbitmap = BitmapFactory.decodeFile(path, newOpts);
                if (tempbitmap == null) {
                    //如果图片为null, 图片不完整则删除掉图片
                    byte[] bytes = new byte[(int) file.length() + 1];
                    FileInputStream inputStream = new FileInputStream(path);
                    inputStream.read(bytes);
                    tempbitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (tempbitmap == null) {
                        file.delete();
                    }
                }
                return tempbitmap;
            } catch (OutOfMemoryError e) {
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            newOpts.inJustDecodeBounds = true;
            tempbitmap = BitmapFactory.decodeFile(path, newOpts);
            int sreen = width * height;
            int image = tempbitmap.getHeight() * tempbitmap.getWidth();
            if (sreen <= 720) {// 防止过小图
                sreen = 720 * 1080;
            }
            int samplesize = image / sreen;
            if (samplesize < 2) {
                samplesize = 2;
            }
            newOpts.inSampleSize = samplesize;
            newOpts.inJustDecodeBounds = false;
            try {
                tempbitmap = BitmapFactory.decodeFile(path, newOpts);
                return tempbitmap;
            } catch (OutOfMemoryError e) {

            } catch (Exception e) {
                e.printStackTrace();


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
