package com.squareup.code;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.squareup.code.databinding.LauncherLayoutBinding;
import com.squareup.code.home.tab.TabsCache;
import com.squareup.code.launcher.LauncherCache;
import com.squareup.code.launcher.LauncherMode;
import com.squareup.code.pay.PayUtils;
import com.squareup.code.views.RadioTextView;
import com.squareup.code.wx.WxpayModel;
import com.squareup.code.wxapi.WXEntryActivity;
import com.squareup.lib.BaseActivity;
import com.squareup.lib.EventMainObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


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
                    ShareNotice.getInstance().show(LauncherActivity.this);

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


//        observable.subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.trampoline())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Thread thread = Thread.currentThread();
//                        thread.getName();
//                        LogUtil.i(thread.getName()+"========3==========");
//
//                        LogUtil.i(integer + "===1============");
//                    }
//                });
//        observable.unsubscribeOn(Schedulers.computation());

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
