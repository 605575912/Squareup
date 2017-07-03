package com.squareup.code.account;

import android.app.Activity;
import android.content.Intent;

import com.squareup.code.BR;
import com.squareup.code.R;
import com.squareup.code.utils.TencentUtils;
import com.squareup.code.utils.URLUtils;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.utils.ToastUtils;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/06/12 0012.
 */

public enum APPAccountManager {
    INSTANCE;
    int state = LOGINFAILSTATE;
    public static final int LOGINSUCCESSSTATE = 1;
    //    public static final int UNLOGINSTATE = 0;
    public static final int LOGININGSTATE = 2;
    public static final int LOGINFAILSTATE = -1;
    private User user;
    TencentUtils tencentUtils;
    IUiListener loginListener;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLoginState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLoginStateText() {
        if (state == LOGINSUCCESSSTATE) {
            return getUser().getName();
        }
        if (state == LOGININGSTATE) {
            return "登录中";
        }
        return "请点击登录";
    }

    private APPAccountManager() {

    }

    //    public static synchronized AccountManager getInstance() {
//        if (accountManager == null) {
//            synchronized (AccountManager.class) {
//                accountManager = new AccountManager();
//            }
//        }
//        return accountManager;
//    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (loginListener == null) {
            return;
        }
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, loginListener);
            }
        }
    }

    public void loginUser(String account, String psw) {
        state = LOGININGSTATE;
        HttpUtils.INSTANCE.getAsynThreadHttp(URLUtils.getInstance().getLoginUrl(), LoginModel.class, new HttpUtils.HttpListener() {
            @Override
            public void success(Object model, String data) {
                if (model instanceof LoginModel) {
                    state = LOGINSUCCESSSTATE;
                    LoginModel loginModel = (LoginModel) model;
                    user = loginModel.getUser();
//                    FileUtils.saveFile(FileUtils.getFile(filename), data);
                } else {
                    state = LOGINFAILSTATE;
                }
            }

            @Override
            public void failed(Object model) {
                state = LOGINFAILSTATE;
            }
        });//返回根据JSON解析的对象
    }

    public void AutoLoginUser(Activity activity, DataBindBaseViewItem.ViewHolder viewHolder) {
        if (state == LOGININGSTATE) {
            return;
        }
        state = LOGININGSTATE;
        AutoLoginQQUser(activity, viewHolder);
    }

    public void loginQQUser(final Activity activity) {

        if (tencentUtils == null) {
            tencentUtils = new TencentUtils();
        }
        tencentUtils.login(activity, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    JSONObject jsonObject = (JSONObject) o;
                    String nickname = jsonObject.getString("nickname");
                    String gender = jsonObject.getString("gender");
                    String province = jsonObject.getString("province");
                    String city = jsonObject.getString("city");
                    String figureurl_qq_2 = jsonObject.getString("figureurl_qq_2");
                    state = LOGINSUCCESSSTATE;

//                    finish();
                } catch (Exception e) {
                    ToastUtils.showToast(activity.getResources().getString(R.string.login_failed));
                }

            }

            @Override
            public void onError(UiError uiError) {
                state = LOGINFAILSTATE;
            }

            @Override
            public void onCancel() {
                state = LOGINFAILSTATE;
            }
        });
    }

    private void AutoLoginQQUser(final Activity activity, final DataBindBaseViewItem.ViewHolder viewHolder) {
        if (tencentUtils == null) {
            tencentUtils = new TencentUtils();
        }
        tencentUtils.autoLogin(activity, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    JSONObject jsonObject = (JSONObject) o;
                    String nickname = jsonObject.getString("nickname");
                    String gender = jsonObject.getString("gender");
                    String province = jsonObject.getString("province");
                    String city = jsonObject.getString("city");
                    String figureurl_qq_2 = jsonObject.getString("figureurl_qq_2");
                    User user = new User();
                    user.setImg(figureurl_qq_2);
                    user.setName(nickname);
                    APPAccountManager.INSTANCE.setUser(user);
                    state = LOGINSUCCESSSTATE;
                    viewHolder.getViewDataBinding().setVariable(BR.accounmanager, APPAccountManager.INSTANCE);
                    viewHolder.getViewDataBinding().setVariable(BR.user, APPAccountManager.INSTANCE.getUser());
                } catch (Exception e) {
                    ToastUtils.showToast(activity.getResources().getString(R.string.login_failed));
                }

            }

            @Override
            public void onError(UiError uiError) {
                state = LOGINFAILSTATE;
            }

            @Override
            public void onCancel() {
                state = LOGINFAILSTATE;
            }
        });
    }


}
