package com.squareup.code.utils;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.squareup.code.MyApplication;
import com.squareup.code.WebViewFactory;
import com.squareup.lib.BaseApplication;
import com.squareup.lib.activity.PoxyActivity;
import com.squareup.lib.utils.LogUtil;

/**
 * Created by Administrator on 2017/06/10 0010.
 */

public class LauncherUrl {
    static final String scheme = "alllib";
    static final String host = "com.lzx.lib";
    static final String path = "/openwith";
    static final String parameter = "type";

    public static Intent launcherActivity(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            Intent intent = PoxyActivity.startIntent(BaseApplication.getApplication(), WebViewFactory.class.getName());
            intent.putExtra(WebViewFactory.EXTRA_NAME_URL, url);
            return intent;
        }
        Uri uri = Uri.parse(url);
        if (host.equals(uri.getHost())) {
            if (scheme.equals(uri.getScheme())) {
                if (path.equals(uri.getPath())) {
                    String type = uri.getQueryParameter(parameter);
                    if ("1".equals(type)) {
                        Intent intent = PoxyActivity.startIntent(BaseApplication.getApplication(), WebViewFactory.class.getName());
                        intent.putExtra(WebViewFactory.EXTRA_NAME_URL, "http://www.baidu.com");
                        return intent;
                    }
                }
            }
        }
        return null;
    }
}
