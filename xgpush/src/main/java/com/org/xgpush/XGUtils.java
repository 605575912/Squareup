package com.org.xgpush;

import android.content.Context;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

/**
 * Created by Administrator on 2017/08/17 0017.
 */

public class XGUtils {
    public static void init(Context context){
        //代码内动态注册access ID
        //XGPushConfig.setAccessId(this,2100250470);
        //开启信鸽的日志输出，线上版本不建议调用
        XGPushConfig.enableDebug(context.getApplicationContext(), true);
                /*
        注册信鸽服务的接口
        如果仅仅需要发推送消息调用这段代码即可
        */
        XGPushManager.registerPush(context.getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
//                        Log.w(Constants.LogTag, "+++ register push sucess. token:" + data+"flag" +flag);

//                        LogUtil.i("==============onSuccess=====");
                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
//                        LogUtil.i("==============onFail=====");

                    }
                });

    }
}
