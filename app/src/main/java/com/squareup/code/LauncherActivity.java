package com.squareup.code;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.code.home.tab.TabsCache;
import com.squareup.code.launcher.LauncherCache;
import com.squareup.code.launcher.LauncherMode;
import com.squareup.code.pay.PayUtils;
import com.squareup.code.utils.TencentUtils;
import com.squareup.code.views.RadioTextView;
import com.squareup.code.wx.WxpayModel;
import com.squareup.code.wxapi.WXEntryActivity;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.utils.LogUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileInputStream;


/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class LauncherActivity extends BaseActivity implements View.OnClickListener {
    LauncherLayoutBinding activityMainBinding;
    LauncherCache launcherCache;
    Handler handler;

    @Override
    protected boolean isAllTranslucentStatus() {
        return true;
    }

    PayUtils payUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.launcher_layout);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    handler.removeCallbacksAndMessages(null);
//                    Intent intent = new Intent(LauncherActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
//                    ShareNotice.getInstance().show(LauncherActivity.this);


//                    tencentUtils = new TencentUtils();
//                    tencentUtils.login(LauncherActivity.this);
//                    tencentUtils.share(LauncherActivity.this, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                    tencentUtils.share(LauncherActivity.this, QQShare.SHARE_TO_QQ_TYPE_APP);
                } else {

                }

            }
        };
//        TwinklingRefreshLayout twinklingRefreshLayout;
        if (payUtils == null) {
//            payUtils = new PayUtils(LauncherActivity.this);
//            payUtils.payV2("data", handler);
        }
        TabsCache tabsCache = new TabsCache();
        tabsCache.dowlNewWorkData();
        launcherCache = new LauncherCache();
        launcherCache.getCacheData();
        launcherCache.dowlNewWorkData();
        //代码内动态注册access ID
        //XGPushConfig.setAccessId(this,2100250470);
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(this,true);


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

    @Override
    public void onClick(View v) {
        if (v instanceof RadioTextView) {
            handler.sendEmptyMessage(0);
        }
    }

}
