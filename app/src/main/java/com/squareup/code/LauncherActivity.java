package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.code.launcher.LauncherCache;
import com.squareup.code.launcher.LauncherMode;
import com.squareup.code.views.RadioTextView;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;


/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class LauncherActivity extends BaseActivity {
    LauncherLayoutBinding activityMainBinding;
    LauncherCache launcherCache = new LauncherCache();
    Handler handler;

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
        launcherPenster = new LauncherPenster();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    removeCallbacksAndMessages(null);
//                    YWCom.INSTANCE.login(LauncherActivity.this,"testpro1","taobao1234");

                    launcherPenster.startHome(LauncherActivity.this);

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
