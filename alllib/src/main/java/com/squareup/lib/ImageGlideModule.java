package com.squareup.lib;


import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.squareup.lib.utils.LogUtil;

import java.io.InputStream;

//
//
///**
// * Created by Administrator on 2017/06/02 0002.
// */
//
public class ImageGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        //设置图片解码格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        builder.setDiskCache()
//        LogUtil.i("ImageGlideModule 自定义缓存");
    }

    @Override
    public void registerComponents(Context context, Registry registry) {
//        Glide.with(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpManager.getInstance().getClient()));
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpUtils.INSTANCE.getmOkHttpClient()));

    }
}
//    public class OkHttpStreamFetcher implements DataFetcher<InputStream> {
//        private final OkHttpClient client;
//        private final GlideUrl url;
//        private InputStream stream;
//        private ResponseBody responseBody;
//
//        public OkHttpStreamFetcher(OkHttpClient client, GlideUrl url) {
//            this.client = client;
//            this.url = url;
//        }
//
//        @Override
//        public InputStream loadData(Priority priority) throws Exception {
//            Request.Builder requestBuilder = new Request.Builder()
//                    .url(url.toStringUrl());
//
//            for (Map.Entry<String, String> headerEntry : url.getHeaders().entrySet()) {
//                String key = headerEntry.getKey();
//                requestBuilder.addHeader(key, headerEntry.getValue());
//            }
//
//            Request request = requestBuilder.build();
//
//            Response response = client.newCall(request).execute();
//            responseBody = response.body();
//            if (!response.isSuccessful()) {
//                throw new IOException("Request failed with code: " + response.code());
//            }
//
//            long contentLength = responseBody.contentLength();
//            stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
//            return stream;
//        }
//
//        @Override
//        public void cleanup() {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    // Ignored
//                }
//            }
//            if (responseBody != null) {
//                try {
//                    responseBody.close();
//                } catch (IOException e) {
//                    // Ignored.
//                }
//            }
//        }
//
//        @Override
//        public String getId() {
//            return url.getCacheKey();
//        }
//
//        @Override
//        public void cancel() {
//            // TODO: call cancel on the client when this method is called on a background thread. See #257
//        }
//    }
//    public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {
//
//        /**
//         * The default factory for {@link OkHttpUrlLoader}s.
//         */
//        public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
//            private OkHttpClient client;
//
//
//
//            /**
//             * Constructor for a new Factory that runs requests using a static singleton client.
//             */
//            public Factory() {
//                this(HttpUtils.getInstance(BaseApplication.application).getmOkHttpClient());
//            }
//
//            /**
//             * Constructor for a new Factory that runs requests using given client.
//             */
//            public Factory(OkHttpClient client) {
//                this.client = client;
//            }
//
//            @Override
//            public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
//                return new OkHttpUrlLoader(client);
//            }
//
//            @Override
//            public void teardown() {
//                // Do nothing, this instance doesn't own the client.
//            }
//        }
//
//        private final OkHttpClient client;
//
//        public OkHttpUrlLoader(OkHttpClient client) {
//            this.client = client;
//        }
//
//        @Override
//        public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
//            return new OkHttpStreamFetcher(client, model);
//        }
//    }
//}
