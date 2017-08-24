package com.org.im;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.IYWP2PPushListener;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMCore;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.contact.IYWContact;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.login.IYWConnectionListener;

import java.util.List;

/**
 * Created by Administrator on 2017/07/04 0004.
 */

public enum YWCom {
    INSTANCE;
    YWIMKit mIMKit;
    public static final String APP_KEY = "23015524";//24533266

    private YWCom() {

    }

    public void login(final Activity activity, String userid, String password) {
//        final String userid = "testpro1";
//此对象获取到后，保存为全局对象，供APP使用
//此对象跟用户相关，如果切换了用户，需要重新获取
        mIMKit = YWAPI.getIMKitInstance(userid, APP_KEY);


//从IMKit对象中获取IMCore
        YWIMCore imCore = mIMKit.getIMCore();
        imCore.addConnectionListener(new IYWConnectionListener() {
            @Override
            public void onDisconnect(int i, String s) {

            }

            @Override
            public void onReConnecting() {

            }

            @Override
            public void onReConnected() {

            }
        });
        imCore.getConversationService().addP2PPushListener(new IYWP2PPushListener() {
            @Override
            public void onPushMessage(IYWContact iywContact, List<YWMessage> list) {
            }
        });
        //开始登录
//        String password = "taobao1234";
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
//                LogUtil.i("onSuccess=============" + arg0);
                Intent intent = mIMKit.getConversationActivityIntent();
                activity.startActivity(intent);
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
//                LogUtil.i("=============" + arg0);
            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
//                LogUtil.i("onError=============" + description);
            }
        });
    }
}
