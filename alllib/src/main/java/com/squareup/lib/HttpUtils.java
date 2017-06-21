package com.squareup.lib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.lib.activity.PermissionsGrantActivity;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.utils.FileUtils;
import com.squareup.lib.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class HttpUtils {
    private static HttpUtils httpUtils;
    private Context application;
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

//    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
//        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
//            try {
//                SSLContext sc = SSLContext.getInstance("TLSv1.2");
//                sc.init(null, null, null);
//                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));
//                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                        .tlsVersions(TlsVersion.TLS_1_2)
//                        .build();
//                List specs = new ArrayList<>();
//                specs.add(cs);
//                specs.add(ConnectionSpec.COMPATIBLE_TLS);
//                specs.add(ConnectionSpec.CLEARTEXT);
//                client.connectionSpecs(specs);
//            } catch (Exception exc) {
//            }
//        }
//        return client;
//    }


    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

//    ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
//            .tlsVersions(TlsVersion.TLS_1_2)
//            .cipherSuites(
//                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
//            .build();

    private HttpUtils(Context application) {
        this.application = application;
//        OkHttpClient.Builder client = new OkHttpClient.Builder()
//                .followRedirects(true)
//                .followSslRedirects(true)
//                .retryOnConnectionFailure(true)
//                .cache(null)
//                .connectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS))
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .writeTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS);
//        mOkHttpClient = enableTls12OnPreLollipop(client).build();
//        mOkHttpClient.setConnectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS));
//        mOkHttpClient.setConnectTimeout(Constants.HTTP_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        mOkHttpClient = new OkHttpClient
                .Builder()
//                .connectionSpecs(Collections.singletonList(spec))
//                .certificatePinner(new CertificatePinner.Builder()
//                        .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
//                        .build())
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cache(new Cache(FileUtils.getFile("cache"), 1000 * 1024))
                .addInterceptor(new CaheInterceptor(application))
                .addNetworkInterceptor(new CaheInterceptor(application)).build();

    }

    public static synchronized HttpUtils getInstance(Context application) {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                httpUtils = new HttpUtils(application);
            }
        }
        return httpUtils;
    }

    HashMap<String, HttpListener> httpListeners = new HashMap<String, HttpListener>();

    public interface HttpListener {
        void success(Object model, String data);

        void failed(Object model);
    }

    public void getAsynMainHttp(final String url, final Class jsonmodel) {
        getAsynHttp(0, url, jsonmodel);
    }

    public String getAsynMainHttp(final File file, final Class jsonmodel) {
        if (file != null && file.exists()) {
            getAsynHttp(0, file.getPath(), jsonmodel);
            return file.getPath();
        } else {
            failed(0, "", "file not exists");
        }
        return "";
    }

    public void getAsynMainHttp(final String url) {
        getAsynHttp(0, url, null);
    }


    public void postAsynMainHttp(final String url, Class jsonmodel, FormBody.Builder builder) {
        postAsynHttp(0, url, jsonmodel, builder);
    }

    public void postAsynMainHttp(String url, FormBody.Builder builder) {
        postAsynHttp(0, url, null, builder);
    }

    public void postAsynThreadHttp(final String url, Class jsonmodel, FormBody.Builder builder) {
        postAsynHttp(1, url, jsonmodel, builder);
    }

    public void postAsynThreadHttp(final String url, Class jsonmodel, FormBody.Builder builder, HttpListener httpListener) {
        if (!TextUtils.isEmpty(url) && httpListener != null) {
            httpListeners.put(url, httpListener);
        }
        postAsynHttp(1, url, jsonmodel, builder);
    }

    public void postAsynThreadHttp(final String url, FormBody.Builder builder) {
        postAsynHttp(1, url, null, builder);
    }

    public void getAsynThreadHttp(final String url, final Class jsonmodel) {
        getAsynHttp(1, url, jsonmodel);
    }

    public void getAsynThreadHttp(final String url, final Class jsonmodel, HttpListener httpListener) {
        if (!TextUtils.isEmpty(url) && httpListener != null) {
            httpListeners.put(url, httpListener);
        }
        getAsynHttp(1, url, jsonmodel);
    }

    public void getAsynThreadHttp(final String url) {
        getAsynHttp(1, url, null);
    }

    private void JSONfor(int type, final String url, final Class jsonmodel, String result) {
        Object model;
        boolean success = false;
        HttpListener httpListener = httpListeners.get(url);
        if (jsonmodel == null || jsonmodel.getName().equals("java.lang.String")) {
            model = result;
            success = true;
            if (httpListener != null) {
                httpListener.success(model, result);
                httpListeners.remove(url);
            }
        } else {
            Gson gson = new Gson();
            try {
                model = gson.fromJson(result, jsonmodel);
                success = true;
                if (httpListener != null) {
                    httpListener.success(model, result);
                    httpListeners.remove(url);
                }
            } catch (JsonSyntaxException exception) {
                model = result;
                if (httpListener != null) {
                    httpListener.failed(model);
                    httpListeners.remove(url);
                }
            }
        }


        if (type == 0) {
            EventMainObject mainObject = new EventMainObject(model);
            mainObject.setCommand(url);
            mainObject.setSuccess(success);
            EventBus.getDefault().post(mainObject);
        } else {
            EventThreadObject threadObject = new EventThreadObject(model);
            threadObject.setCommand(url);
            threadObject.setSuccess(success);
            EventBus.getDefault().post(threadObject);
        }
    }

    private void failed(int type, String url, String fail) {
        HttpListener httpListener = httpListeners.get(url);
        if (httpListener != null) {
            httpListener.failed(fail);
            httpListeners.remove(url);
        }
        if (type == 0) {
            EventMainObject mainObject = new EventMainObject(fail);
            mainObject.setCommand(url);
            EventBus.getDefault().post(mainObject);
        } else {
            EventThreadObject threadObject = new EventThreadObject(fail);
            threadObject.setCommand(url);
            EventBus.getDefault().post(threadObject);
        }
    }

    private void getAsynHttp(final int type, final String url, final Class jsonmodel) {
        if (TextUtils.isEmpty(url) || application == null || !PermissionsGrantActivity.checkAllPermissionsGranted(application, new String[]{
                Manifest.permission.INTERNET})
                ) {
            failed(type, url, "未能获取数据");
            return;
        }
        int index = url.indexOf("file:///android_asset/");
        if (index == 0) {
            ThreadManager.submit(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    try {
                        String temp = url.substring("file:///android_asset/".length());
                        inputStream = application.getAssets().open(temp);
                        InputStreamReader inputReader = new InputStreamReader(inputStream);
                        BufferedReader bufReader = new BufferedReader(inputReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONfor(type, url, jsonmodel, stringBuilder.toString());
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    failed(type, url, "read failed");

                }
            });


        } else if (url.startsWith("https://") || url.startsWith("http://")) {
            Request.Builder requestBuilder = new Request.Builder().url(url);
            Request request = requestBuilder.build();
            Call mcall = mOkHttpClient.newCall(request);
            mcall.enqueue(new

                                  Callback() {
                                      @Override
                                      public void onFailure(Call call, IOException e) {
                                          failed(type, url, "read failed");
                                      }

                                      @Override
                                      public void onResponse(Call call, Response response) throws IOException {
                                          String str = null;
//                                          if (null != response.cacheResponse()) {
//                                              str = response.cacheResponse().toString();
//                                          } else {
                                          if (response.body() != null) {
                                              str = response.body().string();
//                                                  str = response.networkResponse().body().string();
                                          }
//                                          }
                                          if (TextUtils.isEmpty(str)) {
                                              LogUtil.i("read failed");
                                              failed(type, url, "read failed");
                                          } else {
                                              LogUtil.i(str);
                                              JSONfor(type, url, jsonmodel, str);
                                          }
                                      }
                                  });
        } else {
            readFile(type, url, jsonmodel);
        }
    }

    private void postAsynHttp(final int type, final String url, final Class jsonmodel, FormBody.Builder builder) {
        if (TextUtils.isEmpty(url) || application == null || !PermissionsGrantActivity.checkAllPermissionsGranted(application, new String[]{
                Manifest.permission.INTERNET})
                ) {
            return;
        }
        int index = url.indexOf("file:///android_asset/");

        if (index == 0) {
            ThreadManager.submit(new Runnable() {
                @Override
                public void run() {
                    InputStream inputStream = null;
                    try {
                        String temp = url.substring("file:///android_asset/".length());
                        inputStream = application.getAssets().open(temp);
                        InputStreamReader inputReader = new InputStreamReader(inputStream);
                        BufferedReader bufReader = new BufferedReader(inputReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONfor(type, url, jsonmodel, stringBuilder.toString());
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    failed(type, url, "read failed");

                }
            });
        } else if (url.startsWith("https://") || url.startsWith("http://")) {
            if (builder == null) {
                failed(type, url, "builder failed");
                return;
            }
            RequestBody formBody = builder
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failed(type, url, "read failed");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = null;
                    if (null != response.body()) {
                        str = response.body().string();
                    }
                    if (TextUtils.isEmpty(str)) {
                        failed(type, url, "read failed");
                    } else {
                        JSONfor(type, url, jsonmodel, str);
                    }
                }
            });
        } else {
            readFile(type, url, jsonmodel);
        }

    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    private String getNameFromUrl(String url) {
        int i = url.lastIndexOf("/");
        int n = url.lastIndexOf(".");
        if (i > -1 && n > -1) {
            return AppLibUtils.getMd5(url) + url.substring(i + 1);
        }
        return AppLibUtils.getMd5(url);
    }

    /**
     * @param url      下载连接
     * @param listener 下载监听
     */
    public void download(final String url, final OnDownloadListener listener) {
        final Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
//                String savePath = FileUtils.readFile(getNameFromUrl(url));
                try {
                    File file = FileUtils.getFile(getNameFromUrl(url));
                    if (file == null) {
                        listener.onDownloadFailed();
                        return;
                    }
                    ResponseBody body = response.body();
                    if (body == null) {
                        listener.onDownloadFailed();
                        return;
                    }
                    is = body.byteStream();
                    long total = body.contentLength();
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private void readFile(final int type, final String url, final Class jsonmodel) {
        ThreadManager.submit(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    File file = new File(url);
                    if (file.exists()) {
                        inputStream = new FileInputStream(file);
                        InputStreamReader inputReader = new InputStreamReader(inputStream);
                        BufferedReader bufReader = new BufferedReader(inputReader);
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        JSONfor(type, url, jsonmodel, stringBuilder.toString());
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                failed(type, url, "read failed");

            }
        });
    }
}
