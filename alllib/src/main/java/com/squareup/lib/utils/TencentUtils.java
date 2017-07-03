package com.squareup.lib.utils;

import android.app.Activity;
import android.content.Intent;

import com.squareup.lib.BaseApplication;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * Created by Administrator on 2017/07/03 0003.
 */

public class TencentUtils {
    String APP_ID = "1106186071";
    IUiListener loginListener;

    public void login(Activity activity) {
        Tencent mTencent = Tencent.createInstance(APP_ID, BaseApplication.application);
        if (!mTencent.isSessionValid()) {
            loginListener = new BaseUiListener();
//            应用需要获得哪些API的权限，由“，”分隔。
//            例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
            mTencent.login(activity, "all", loginListener);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, loginListener);
            }
        }
    }

    public void logout() {
        Tencent mTencent = Tencent.createInstance(APP_ID, BaseApplication.application);
        if (!mTencent.isSessionValid()) {
            mTencent.logout(BaseApplication.application);
        }

    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
//            {"ret":0,"openid":"746C24658F4F0840DB1684E55191DBFF","access_token":"82B833882A50B72904A7B9CAC0809158","pay_token":"5AC2354CFE49578495255AC035991F08","expires_in":7776000,"pf":"desktop_m_qq-10000144-android-2002-","pfkey":"ba34797aed4f967781d558065898d77d","msg":"","login_cost":192,"query_authority_cost":105,"authority_cost":0}
            try {
                JSONObject jsonObject = (JSONObject) o;
                String openid = jsonObject.getString("openid");
                long expires_in = jsonObject.getLong("expires_in");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError e) {
//            showResult("onError:", "code:" + e.errorCode + ", msg:"
//                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
//            showResult("onCancel", "");
        }
    }

    private class BaseApiListener implements IRequestListener {
        @Override
        public void onComplete(final JSONObject response) {
//            showResult("IRequestListener.onComplete:", response.toString());
            doComplete(response);
        }

        protected void doComplete(JSONObject response) {
        }

        @Override
        public void onIOException(final IOException e) {
//            showResult("IRequestListener.onIOException:", e.getMessage());
        }

        @Override
        public void onMalformedURLException(final MalformedURLException e) {
//            showResult("IRequestListener.onMalformedURLException", e.toString());
        }

        @Override
        public void onJSONException(final JSONException e) {
//            showResult("IRequestListener.onJSONException:", e.getMessage());
        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException arg0
        ) {
// TODO Auto-generated method stub
        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException arg0
        ) {
// TODO Auto-generated method stub
        }

        //1.4版本中IRequestListener 新增两个异常
        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {
// 当前网络不可用时触发此异常
        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {
// http请求返回码非200时触发此异常
        }

        public void onUnknowException(Exception e) {
// 出现未知错误时会触发此异常
        }
    }

}
