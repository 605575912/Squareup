package com.squareup.lib;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.lib.activity.PermissionsGrantActivity;
import com.squareup.lib.utils.FileUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class HttpUtils {
    private static HttpUtils httpUtils;
    private Context application;
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    protected Picasso getPicasso() {
        OkHttpClient client = getProgressBarClient();
        return new Picasso.Builder(application)
                .downloader(new ImageDownLoader(client))
                .build();
    }

    private OkHttpClient getProgressBarClient() {
        return HttpUtils.getInstance(application).mOkHttpClient.newBuilder()
                .addInterceptor(new CaheInterceptor(application))
                .addNetworkInterceptor(new CaheInterceptor(application))
                .build();
    }

    private HttpUtils(Context application) {
        this.application = application;
        mOkHttpClient = new OkHttpClient
                .Builder()
                .cache(new Cache(FileUtils.getFile("cache"), 1000 * 1024))
                .addInterceptor(new CaheInterceptor(application))
                .addNetworkInterceptor(new CaheInterceptor(application))
                .build();
    }

    public static synchronized HttpUtils getInstance(Context application) {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                httpUtils = new HttpUtils(application);
            }
        }
        return httpUtils;
    }

    public void getAsynMainHttp(final String url, final Class jsonmodel) {
        getAsynHttp(0, url, jsonmodel);
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

    public void postAsynThreadHttp(final String url, FormBody.Builder builder) {
        postAsynHttp(1, url, null, builder);
    }

    public void getAsynThreadHttp(final String url, final Class jsonmodel) {
        getAsynHttp(1, url, jsonmodel);
    }

    public void getAsynThreadHttp(final String url) {
        getAsynHttp(1, url, null);
    }

    private void JSONfor(int type, final String url, final Class jsonmodel, String result) {
        Object model;
        boolean success = false;
        if (jsonmodel == null || jsonmodel.getName().equals("java.lang.String")) {
            model = result;
            success = true;
        } else {
            Gson gson = new Gson();
            try {
                model = gson.fromJson(result, jsonmodel);
                success = true;
            } catch (JsonSyntaxException exception) {
                model = result;
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
            return;
        }
        int index = url.indexOf("file:///android_asset/");
        if (index == 0) {
            ThreadManager.post(new Runnable() {
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
            //可以省略，默认是GET请求
            requestBuilder.method("GET", null);
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
                                          if (null != response.cacheResponse()) {
                                              str = response.cacheResponse().toString();
                                          } else {
                                              if (response.networkResponse() != null) {
                                                  str = response.networkResponse().toString();
                                              }
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

    private void postAsynHttp(final int type, final String url, final Class jsonmodel, FormBody.Builder builder) {
        if (TextUtils.isEmpty(url) || application == null || !PermissionsGrantActivity.checkAllPermissionsGranted(application, new String[]{
                Manifest.permission.INTERNET})
                ) {
            return;
        }
        int index = url.indexOf("file:///android_asset/");

        if (index == 0) {
            ThreadManager.post(new Runnable() {
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
                    if (null != response.cacheResponse()) {
                        str = response.cacheResponse().toString();
                    } else {
                        if (response.networkResponse() != null) {
                            str = response.networkResponse().toString();
                        }
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

    private void readFile(final int type, final String url, final Class jsonmodel) {
        ThreadManager.post(new Runnable() {
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
