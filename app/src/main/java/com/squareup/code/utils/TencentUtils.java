package com.squareup.code.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.squareup.lib.BaseApplication;
import com.squareup.lib.utils.AppLibUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
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
    private String APP_ID = "1106186071";
    IUiListener loginListener;

    public void login(final Activity activity, final IUiListener iUiListener) {
        final Tencent mTencent = Tencent.createInstance(APP_ID, BaseApplication.application);
        if (!mTencent.isSessionValid()) {
            loginListener = new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    //            {"ret":0,"openid":"746C24658F4F0840DB1684E55191DBFF","access_token":"82B833882A50B72904A7B9CAC0809158","pay_token":"5AC2354CFE49578495255AC035991F08","expires_in":7776000,"pf":"desktop_m_qq-10000144-android-2002-","pfkey":"ba34797aed4f967781d558065898d77d","msg":"","login_cost":192,"query_authority_cost":105,"authority_cost":0}
                    try {
                        JSONObject jsonObject = (JSONObject) o;
                        String openid = jsonObject.getString("openid");
                        String access_token = jsonObject.getString("access_token");
                        long expires_in = jsonObject.getLong("expires_in");
                        QQToken token = new QQToken(access_token);
                        token.setAccessToken(access_token, String.valueOf(expires_in));
                        token.setOpenId(openid);
                        token.setAppId(APP_ID);
                        UserInfo userInfo = new UserInfo(activity.getApplication(), token);
                        userInfo.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(Object o) {
                                //{"ret":-1,"msg":"client request's parameters are invalid, invalid openid"}
//{"ret":0,"msg":"","is_lost":0,"nickname":"梁振雄","gender":"男","province":"广东","city":"广州","figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1106186071\/746C24658F4F0840DB1684E55191DBFF\/30","figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1106186071\/746C24658F4F0840DB1684E55191DBFF\/50","figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1106186071\/746C24658F4F0840DB1684E55191DBFF\/100","figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1106186071\/746C24658F4F0840DB1684E55191DBFF\/40","figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1106186071\/746C24658F4F0840DB1684E55191DBFF\/100","is_yellow_vip":"0","vip":"0","yellow_vip_level":"0","level":"0","is_yellow_year_vip":"0"}
                                try {
                                    JSONObject jsonObject = (JSONObject) o;
                                    if (jsonObject.has("ret")) {
                                        long ret = jsonObject.getLong("ret");
                                        if (ret == 0) {
                                            String nickname = jsonObject.getString("nickname");
                                            String gender = jsonObject.getString("gender");
                                            String province = jsonObject.getString("province");
                                            String city = jsonObject.getString("city");
                                            String figureurl_qq_2 = jsonObject.getString("figureurl_qq_2");
                                            if (iUiListener != null) {
                                                iUiListener.onComplete(o);
                                            }
                                            return;
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                if (iUiListener != null) {
                                    UiError uiError = new UiError(-1, "", "");
                                    iUiListener.onError(uiError);
                                }
                            }

                            @Override
                            public void onError(UiError uiError) {
                                if (iUiListener != null) {
                                    iUiListener.onError(uiError);
                                }
                            }

                            @Override
                            public void onCancel() {
                                if (iUiListener != null) {
                                    iUiListener.onCancel();
                                }
                            }
                        });
                    } catch (Exception e) {
                        if (iUiListener != null) {
                            UiError uiError = new UiError(-1, "", "");
                            iUiListener.onError(uiError);
                        }
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    if (iUiListener != null) {
                        iUiListener.onCancel();
                    }
                }

                @Override
                public void onCancel() {
                    if (iUiListener != null) {
                        iUiListener.onCancel();
                    }
                }
            };
//            应用需要获得哪些API的权限，由“，”分隔。
//            例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
            mTencent.login(activity, "all", loginListener);
        } else {
            if (iUiListener != null) {
                UiError uiError = new UiError(-1, "", "");
                iUiListener.onError(uiError);
            }
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

    public void share(Activity activity, int shareType) {
        final Bundle params = new Bundle();
        if (shareType != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "title");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "summary");
        }
        if (shareType == QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, "http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/fenxiangxiaoxidaoQQ-dingxiangfenxiang-01.png");
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/fenxiangxiaoxidaoQQ-dingxiangfenxiang-01.png");
        }
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, AppLibUtils.getApplicationName(BaseApplication.application));
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);//好友
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//Qzone
        if (shareType == QQShare.SHARE_TO_QQ_TYPE_AUDIO) {
//            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, mEditTextAudioUrl.getText().toString());
        }
//        if ((mExtarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN) != 0) {
//            showToast("在好友选择列表会自动打开分享到qzone的弹窗~~~");
//        } else if ((mExtarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE) != 0) {
//            showToast("在好友选择列表隐藏了qzone分享选项~~~");
//        }
        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    JSONObject jsonObject = (JSONObject) o;
                    if (jsonObject.has("ret")) {
                        int ret = jsonObject.getInt("ret");
                        if (ret == 0) {
                            //
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
        Tencent mTencent = Tencent.createInstance(APP_ID, BaseApplication.application);
        mTencent.shareToQQ(activity, params, loginListener);
    }
}
