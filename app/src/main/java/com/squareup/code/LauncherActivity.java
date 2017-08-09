package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.code.home.tab.TabsCache;
import com.squareup.code.launcher.LauncherCache;
import com.squareup.code.launcher.LauncherMode;
import com.squareup.code.pay.PayUtils;
import com.squareup.code.views.RadioTextView;
import com.squareup.code.wx.WxpayModel;
import com.squareup.code.wxapi.WXEntryActivity;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.BuildConfig;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.utils.LogUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


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

    PayUtils payUtils;
    MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.launcher_layout);
//        SimpleDraweeView iv_ = (SimpleDraweeView) activityMainBinding.getRoot().findViewById(R.id.iv_);
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setUri("http://192.168.30.13:8080/img/m.webp")
//                .setAutoPlayAnimations(true)
//                .build();
//        iv_.setController(controller);
        launcherPenster = new LauncherPenster();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    removeCallbacksAndMessages(null);
//                    YWCom.INSTANCE.login(LauncherActivity.this,"testpro1","taobao1234");

//                    launcherPenster.startHome(LauncherActivity.this);
//                    ShareNotice.getInstance().show(LauncherActivity.this);

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
//        TwinklingRefreshLayout twinklingRefreshLayout;
        if (payUtils == null) {
//            payUtils = new PayUtils(LauncherActivity.this);
//            payUtils.payV2("data", handler);
        }

// 开发时调用，建议上线前关闭，以免影响性能
        StatService.setDebugOn(BuildConfig.DEBUG);
        StatService.start(this);
//        StatService.setAppChannel();...)


        //代码内动态注册access ID
        //XGPushConfig.setAccessId(this,2100250470);
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this, true);
        launcherPenster.workCache(launcherCache, new TabsCache());

                /*
        注册信鸽服务的接口
        如果仅仅需要发推送消息调用这段代码即可
        */
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
//                        Log.w(Constants.LogTag, "+++ register push sucess. token:" + data+"flag" +flag);

                        LogUtil.i("==============onSuccess=====");
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        LogUtil.i("==============onFail=====");
//                        Log.w(Constants.LogTag,
//                                "+++ register push fail. token:" + data
//                                        + ", errCode:" + errCode + ",msg:");

                    }
                });

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

    LauncherPenster launcherPenster;


    private void weixinpay(String id) {
        IWXAPI mapi = null;
        if (mapi == null) {
            mapi = WXAPIFactory.createWXAPI(LauncherActivity.this, WXEntryActivity.APP_ID);
        }
        if (mapi.isWXAppInstalled()) {
            mapi.registerApp(WXEntryActivity.APP_ID);
            boolean isPaySupported = mapi.getWXAppSupportAPI() >= com.tencent.mm.opensdk.constants.Build.PAY_SUPPORTED_SDK_INT;
            if (!isPaySupported) {
                //不能支付
                return;
            }
            WxpayModel wxpayModel = new WxpayModel();
            PayReq req = new PayReq();
            req.appId = WXEntryActivity.APP_ID;
            req.partnerId = wxpayModel.getPartnerid();
            req.prepayId = wxpayModel.getPrepayid();
            req.nonceStr = wxpayModel.getNoncestr();
            req.timeStamp = String.valueOf(wxpayModel.getTimestamp());
            req.packageValue = wxpayModel.getPackageX();
            req.sign = wxpayModel.getSign();
            req.extData = "app data"; // optional
            mapi.sendReq(req);

        }
    }

    @Override
    public void onEventMain(EventMainObject event) {
        if (event.getCommand().equals(launcherCache.getCommand())) {
            if (event.getData() instanceof LauncherMode) {
                LauncherMode launcherMode = (LauncherMode) event.getData();
                if (launcherMode.getItems() != null && launcherMode.getItems().size() > 0) {
                    launcherMode.getItems().get(0).setCounttime(5);
                    activityMainBinding.setItemsbean(launcherMode.getItems().get(0));
                    handler.sendEmptyMessageDelayed(0, 5000);
                    handler.sendEmptyMessageDelayed(1, 5500);
                } else {
                    handler.sendEmptyMessageDelayed(0, 1000);
                }
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
