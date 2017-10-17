package com.squareup.lib.http;

import android.content.Context;

import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.utils.NetWorkManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class CaheInterceptor implements Interceptor {

    private Context context;

    public CaheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.url().toString().contains("act=Login")) {
            return chain.proceed(request);
        }
        LogUtil.i("=====" + request.url());
        if (NetWorkManager.isNetworkAvailable(context)) {
            Response response = chain.proceed(request);
            // read from cache for 60 s
            int maxAge = 300;
            String cacheControl = request.cacheControl().toString();
//                Log.e("Tamic", maxAge + "s load cahe:" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
//                Log.e("Tamic", " no network load cahe");
            request = request.newBuilder()
//                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cahe times is 3 days
            int maxStale = 60 * 60 * 24 * 2;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
