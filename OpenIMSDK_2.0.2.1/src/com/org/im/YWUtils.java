package com.org.im;

import android.app.Application;
import android.content.Context;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.wxlib.util.SysUtil;

/**
 * Created by Administrator on 2017/08/17 0017.
 */

public class YWUtils {
    public void init(Application context){
        //必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(context);
        if (SysUtil.isTCMSServiceProcess(context)) {
            return;
        }
//第一个参数是Application Context
//这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(context, YWCom.APP_KEY);
        }
    }
}
