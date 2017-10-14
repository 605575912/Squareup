package com.squareup.code

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import com.squareup.code.launcher.LauncherCache
import com.squareup.code.launcher.LauncherMode
import com.squareup.code.views.RadioTextView
import com.squareup.lib.EventMainObject
import com.squareup.lib.activity.BaseActivity
import com.squareup.lib.utils.LogUtil
import kotlinx.android.synthetic.main.launcher_layout.*

/**
 * Created by Administrator on 2017/05/31 0031.
 */

class LauncherActivity : BaseActivity() {
    internal var launcherCache = LauncherCache()
    internal var handler: Handler? = null


    override fun isAllTranslucentStatus(): Boolean {
        return true
    }

    internal var musicPlayer: MusicPlayer? = null
    internal var launcherPenster: LauncherPenster = LauncherPenster()

    override fun setFromLayoutID(): Int {
        return R.layout.launcher_layout
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        ToastUtils.showToast(Build.VERSION.SDK_INT + "=" + Build.VERSION.RELEASE);
        //http://q.qlogo.cn/qqapp/1105650145/AD0774282F746F5E2E3DEDB4CEA09411/100
//        launcherPenster = LauncherPenster()
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    removeCallbacksAndMessages(null)
                    //                    YWCom.INSTANCE.login(LauncherActivity.this,"testpro1","taobao1234");
                    //                    MyRunnable runnable = new MyRunnable(LauncherActivity.this, 1);
                    //                    submit(1, runnable);
                    test_tv_radio.text = "out";
                    launcherPenster.startHome(this@LauncherActivity)

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
        }

        launcherPenster.initService(launcherCache)
//        val i: Byte = 0

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

    override fun onPause() {
        super.onPause()
        LogUtil.e("1onpause")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.e("1onRestart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e("1onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e("1onDestroy")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.e("1onStart")
    }

    override fun onStop() {
        super.onStop()
        handler!!.removeCallbacksAndMessages(null)
        LogUtil.e("1onStop")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.i("============")
    }


    override fun onEventMain(event: EventMainObject<*>) {
        if (event.command == launcherCache.command) {
            if (event.data is LauncherMode) {
                launcherPenster.lanuncher(handler, viewDataBinding, event.data as LauncherMode)
            } else {
                handler!!.sendEmptyMessageDelayed(0, 1000)
            }
        }
    }

    fun onFinishClick(v: View) {
        if (v is RadioTextView && handler != null) {
            handler!!.sendEmptyMessage(0)
        }
    }

}