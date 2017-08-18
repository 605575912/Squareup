package com.squareup.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.lib.utils.FileUtils;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

import java.io.File;

import okhttp3.Address;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Demo Application示例.
 *
 * @author wenjiewu
 * @since 2017/1/3
 */
public class BaseApplication extends Application {
    private static Application application;

    public static Application getApplication() {
//        return TestBaseApplication.application;
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(application)
//                .setBaseDirectoryName("Fresco")
//                .setBaseDirectoryPathSupplier(new Supplier<File>() {
//                    @Override
//                    public File get() {
//                        return new File(FileUtils.getDiskCacheDir());
//                    }
//                }).build();
//        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
//                .newBuilder(application, HttpUtils.INSTANCE.getmOkHttpClient())
////                . // other setters
////    . // setNetworkFetchProducer is already called for you
//                .setDownsampleEnabled(true)
//                .setMainDiskCacheConfig(diskCacheConfig)
//                .build();
//        Fresco.initialize(application, config);


        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(application)
                .setBaseDirectoryName("Fresco")
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return new File(FileUtils.getDiskCacheDir());
                    }
                }).build();
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(application);
        // 设置内存配置
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                cacheSize, // Max total size of elements in the cache
                200,                     // Max entries in the cache
                10 * 1024 * 1024, // Max total size of elements in eviction queue
                50,                     // Max length of eviction queue
                maxMemory / 8);
        configBuilder.setBitmapMemoryCacheParamsSupplier(
                new Supplier<MemoryCacheParams>() {
                    public MemoryCacheParams get() {
                        return bitmapCacheParams;
                    }
                });
        configBuilder.setMainDiskCacheConfig(diskCacheConfig);
        configBuilder.setDownsampleEnabled(true);
        Fresco.initialize(application, configBuilder.build());

//        ViewTarget.setTagId(com.squareup.lib.R.id.glide_id);


        setStrictMode();
        // 设置是否关闭热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = false;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;
//        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
        /**
         *  全量升级状态回调
         */
        /*Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {

            }

            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                Toast.makeText(getApplicationContext(), "最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgrading(boolean b) {
                Toast.makeText(getApplicationContext(), "onUpgrading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }
        };*/

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = new BetaPatchListener() {
            @Override
            public void onPatchReceived(String patchFileUrl) {
//                Toast.makeText(getApplicationContext(), patchFileUrl, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadReceived(long savedLength, long totalLength) {
//                Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(),
//                        "%s %d%%",
//                        Beta.strNotificationDownloading,
//                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadSuccess(String patchFilePath) {
//                Toast.makeText(getApplicationContext(), patchFilePath, Toast.LENGTH_SHORT).show();
                Beta.applyDownloadedPatch();
            }

            @Override
            public void onDownloadFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplySuccess(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onApplyFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPatchRollback() {

            }
        };


        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
//        Bugly.init(this, "b5f9e8654b", true);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 如果想更新so，可以将System.loadLibrary替换成Beta.loadLibrary
     */
    static {
        Beta.loadLibrary("mylib");
    }

    protected void enabledStrictMode() {
        if (SDK_INT >= 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
//        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
        // 安装tinker
        Beta.installTinker();
    }


    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public static void Exit() {
        try {
            System.exit(0);
        } catch (
                Exception e) {
        }
    }

}
