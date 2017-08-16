package com.squareup.code;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.code.launcher.LauncherCache;
import com.squareup.code.launcher.LauncherMode;
import com.squareup.code.views.RadioTextView;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class LauncherActivity extends BaseActivity {
    LauncherLayoutBinding activityMainBinding;
    LauncherCache launcherCache = new LauncherCache();
    Handler handler;

    private void downLoadImg(final Context context, final String url) {
//        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
//                .setProgressiveRenderingEnabled(true).build();
//
//        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline()
//                .fetchDecodedImage(imageRequest, context);
//
//        DataSubscriber<CloseableReference<CloseableImage>> dataSubscriber =
//                new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
//                    @Override
//                    protected void onNewResultImpl(
//                            DataSource<CloseableReference<CloseableImage>> dataSource) {
//                        if (!dataSource.isFinished()) {
//                            return;
//                        }
//                        CloseableReference<CloseableImage> ref = dataSource.getResult();
//                        if (ref != null) {
//                            try {
//                                CloseableImage result = ref.get();
//                            } finally {
//                                CloseableReference.closeSafely(ref);
//                            }
//                        }
//                    }
//
//                    @Override
//                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//                        Throwable t = dataSource.getFailureCause();
//                    }
//
//                };
//
//        dataSource.subscribe(dataSubscriber, CallerThreadExecutor.getInstance());


        ImageRequest imageRequest1 = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource1 = imagePipeline.fetchDecodedImage(imageRequest1, this);
        dataSource1.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                if (isImageDownloaded(Uri.parse(url), context)) {
                    File file1 = getCachedImageOnDisk(Uri.parse(url), context);
                    ImageFormat imageFormat = ImageFormatChecker.getImageFormat(file1.getPath());
                    File newfiel = new File(file1.getPath().replace(".cnt", "." + imageFormat.getName()));
                    file1.renameTo(newfiel);
                    if (newfiel.exists()) {
                    }

                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                Log.i("TAG", "==");
            }
        }, CallerThreadExecutor.getInstance());


//        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
//                .setFadeDuration(300)
////                .setPlaceholderImage(defaultDrawable)
////                .setFailureImage(defaultDrawable)
//                .setProgressBarImage(new ProgressBarDrawable())
//                .build();
//        DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, this);
//
//        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
//                .setOldController(draweeHolder.getController())
//                .setImageRequest(imageRequest)
//                .build();
//        controller.onClick();

    }

    public static boolean isImageDownloaded(Uri loadUri, Context context) {
        if (loadUri == null) {
            return false;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), context);
        return ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey) || ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey);
    }

    //return file or null
    public static File getCachedImageOnDisk(Uri loadUri, Context context) {
        File localFile = null;
        if (loadUri != null) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), context);
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }

    @Override
    protected boolean isAllTranslucentStatus() {
        return true;
    }

    MusicPlayer musicPlayer;
    LauncherPenster launcherPenster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.launcher_layout);
//        ToastUtils.showToast(Build.VERSION.SDK_INT + "=" + Build.VERSION.RELEASE);
        //http://q.qlogo.cn/qqapp/1105650145/AD0774282F746F5E2E3DEDB4CEA09411/100
        launcherPenster = new LauncherPenster();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    removeCallbacksAndMessages(null);
//                    YWCom.INSTANCE.login(LauncherActivity.this,"testpro1","taobao1234");
                    downLoadImg(getApplicationContext(), "http://q.qlogo.cn/qqapp/1105650145/AD0774282F746F5E2E3DEDB4CEA09411/100");//gif
//                    launcherPenster.startHome(LauncherActivity.this);

//                    tencentUtils = new TencentUtils();
//                    tencentUtils.login(LauncherActivity.this);
//                    tencentUtils.share(LauncherActivity.this, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                    tencentUtils.share(LauncherActivity.this, QQShare.SHARE_TO_QQ_TYPE_APP);

//                    musicPlayer = new MusicPlayer();
//                    musicPlayer.download("http://192.168.30.13:8080/music/a.mp3", new MusicPlayer.OnDownloadListener() {
//                        @Override
//                        public void onDownloadSuccess(File file) {
//                            Log.i("TAG", "========" + file.getPath());
//                            try {
//                                musicPlayer.play(file.getPath());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onDownloading(int progress) {
//
//                        }
//
//                        @Override
//                        public void onDownloadFailed() {
//
//                        }
//                    });


                } else {

                }

            }
        };

        launcherPenster.initService(launcherCache);

//        Observable observable = Observable.create(new ObservableOnSubscribe() {
//            @Override
//            public void subscribe(ObservableEmitter e) throws Exception {
//                e.onNext("Hello");
//                e.onNext("Hi");
//                e.onNext("Aloha");
//                e.onComplete();
//            }
//        });
//        Flowable flowable = Flowable.create(new FlowableOnSubscribe() {
//            @Override
//            public void subscribe(FlowableEmitter e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onComplete();
//            }
//        }, BackpressureStrategy.BUFFER);
//        Flowable map = flowable.map(new Function3<String, String, String, Integer>() {
//            @Override
//            public Integer apply(String s, String s2, String s3) throws Exception {
//                return 11;
//            }
//        });

//        flowable.subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.trampoline())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//
//                        LogUtil.i(integer + "===2============");
//                    }
//
//                });
//
//        flowable.unsubscribeOn(Schedulers.computation());
//
//
//        ResourceSubscriber<Integer> subscriber = new ResourceSubscriber<Integer>() {
//            @Override
//            public void onStart() {
//                request(Long.MAX_VALUE);
//                LogUtil.i("===onStart============");
//            }
//
//            @Override
//            public void onNext(Integer t) {
//                LogUtil.i(t + "===t============");
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                LogUtil.i("===onError============");
//                t.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtil.i("===Done============");
//            }
//        };
//        //创建一个上游 Observable：
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//            }
//        });
//        //创建一个下游 Observer
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                LogUtil.i("subscribe");
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                LogUtil.i("11" + value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtil.i("error");
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtil.i("complete");
//            }
//        };
//        //建立连接
//        observable.subscribe(observer);
//        Flowable.range(1, 10).delay(2, TimeUnit.SECONDS).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                LogUtil.i(integer + "===integer============");
//            }
//        });
//
//        subscriber.dispose();

//
//        HttpUtils.getInstance(getApplication()).download("https://imgjd3.fruitday.com/images/2017-06-08/9ccb2bcf569412e733570ef949fec618.jpg", new HttpUtils.OnDownloadListener() {
//            @Override
//            public void onDownloadSuccess() {
//                ToastUtils.showToast("onDownloadSuccess");
//            }
//
//            @Override
//            public void onDownloading(int progress) {
//                ToastUtils.showToast("onDownloading");
//            }
//
//            @Override
//            public void onDownloadFailed() {
//                ToastUtils.showToast("onDownloadFailed");
//            }
//        });
    }

    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getCommand().equals(launcherCache.getCommand())) {
            if (event.getData() instanceof LauncherMode) {
                launcherPenster.lanuncher(handler, activityMainBinding, (LauncherMode) event.getData());
            } else {
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    }

    public void onFinishClick(View v) {
        if (v instanceof RadioTextView && handler != null) {
            handler.sendEmptyMessage(0);
        }
    }

}
