package com.squareup.code.account;

import com.squareup.code.MyApplication;
import com.squareup.code.utils.URLUtils;
import com.squareup.lib.HttpUtils;

/**
 * Created by Administrator on 2017/06/12 0012.
 */

public enum APPAccountManager {
    INSTANCE;
    int state = 0;
    final int LUCCESSSTATE = 1;
    final int UNLOGINSTATE = 0;
    final int LOGININGSTATE = 2;
    final int LOGINFAILSTATE = -1;
    private User user;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public int getLoginState() {
        return state;
    }

    public String getLoginStateText() {
        if (state == LUCCESSSTATE) {
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

    public void loginUser(String account, String psw) {
        state = LOGININGSTATE;
        HttpUtils.INSTANCE.getAsynThreadHttp(URLUtils.getInstance().getLoginUrl(), LoginModel.class, new HttpUtils.HttpListener() {
            @Override
            public void success(Object model, String data) {
                if (model instanceof LoginModel) {
                    state = LUCCESSSTATE;
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

}
