package com.squareup.code.account;

import com.squareup.code.utils.URLUtils;
import com.squareup.lib.HttpUtils;

/**
 * Created by Administrator on 2017/06/12 0012.
 */

public enum APPAccountManager {
    INSTANCE;
    int state = LOGINFAILSTATE;
    public static final int SUCCESSSTATE = 1;
//    public static final int UNLOGINSTATE = 0;
    public static final int LOGININGSTATE = 2;
    public static final int LOGINFAILSTATE = -1;
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

    public void setState(int state) {
        this.state = state;
    }

    public String getLoginStateText() {
        if (state == SUCCESSSTATE) {
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
                    state = SUCCESSSTATE;
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
