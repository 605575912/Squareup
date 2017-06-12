package com.squareup.code.account;

import com.squareup.code.MyApplication;
import com.squareup.code.utils.URLUtils;
import com.squareup.lib.HttpUtils;

/**
 * Created by Administrator on 2017/06/12 0012.
 */

public class AccountManager {
    static AccountManager accountManager;
    int state = 0;
    final int LUCCESSSTATE = 1;
    final int UNLOGINSTATE = 0;
    final int LOGININGSTATE = 2;
    final int LOGINFAILSTATE = -1;

    public int getLoginState() {
        return state;
    }

    private AccountManager() {

    }

    public static synchronized AccountManager getInstance() {
        if (accountManager == null) {
            synchronized (AccountManager.class) {
                accountManager = new AccountManager();
            }
        }
        return accountManager;
    }

    public void loginUser(String account, String psw) {
        state = LOGININGSTATE;
        HttpUtils.getInstance(MyApplication.application).getAsynThreadHttp(URLUtils.getInstance().getLoginUrl(), LoginModel.class, new HttpUtils.HttpListener() {
            @Override
            public void success(Object model, String data) {
                if (model instanceof LoginModel) {
                    state = LUCCESSSTATE;
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
