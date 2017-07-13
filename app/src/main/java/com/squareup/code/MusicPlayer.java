package com.squareup.code;

import android.media.MediaPlayer;
import android.util.Log;

import com.squareup.lib.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by lzx on 2017/07/13 0013.
 */

public class MusicPlayer {
    private MediaPlayer mp;
    private OkHttpClient mOkHttpClient;

    public MusicPlayer() {
        mp = new MediaPlayer();
        mOkHttpClient = new OkHttpClient();
    }

    public void play(String filepath) throws IOException {
        File file = new File(filepath);
        if (file.exists()) {
            mp.setDataSource(filepath);
            mp.setLooping(true);
            mp.prepare();
//            mp.seekTo(20230);
            mp.start();
        } else {
        }
    }

    //    public void play(String filepath) {
//        try {
//            File file = new File(filepath);
//            if (file.exists()) {
//                FileInputStream is = new FileInputStream(file);
//                FileDescriptor fd = is.getFD();
//                mp.setDataSource(fd);
//                is.close();
//            } else {
//                throw new IOException("setDataSource failed.");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void stop() throws IOException {
        mp.stop();
        mp.release();
    }


    public void download(final String url, final OnDownloadListener listener) {

        final File file = FileUtils.getFile(getNameFromUrl(url));
//        if (file.length() > 100) {
//            listener.onDownloadSuccess(file);
//            return;
//        }
        int downloadLength = 0;
        int contentLength = 1000;
//        .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
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
                try {
                    File tempfile = FileUtils.getFile("musictemp");
                    ResponseBody body = response.body();
                    if (body == null) {
                        listener.onDownloadFailed();
                        return;
                    }
                    long contentLength = body.contentLength();

                    Log.i("TAG", "===========contentLength" + contentLength);
                    if (contentLength <= 0) {
                        listener.onDownloadFailed();
                        return;
                    }
                    is = body.byteStream();
                    fos = new FileOutputStream(tempfile);
//                    RandomAccessFile raf = new RandomAccessFile(file, "rwd");
//                    raf.setLength(contentLength);
//                    while ((len = is.read(buf)) != -1) {
//                        raf.write(buf, 0, len);
//                    }
//                    is.close();
//                    raf.close();

                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / contentLength * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    tempfile.renameTo(file);
                    listener.onDownloadSuccess(file);
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

    private String getNameFromUrl(String url) {
        int i = url.lastIndexOf("/");
        int n = url.lastIndexOf(".");
        if (i > -1 && n > -1 && i < url.length() - 1) {
            return url.substring(i + 1);
        }
        if (i > -1) {
            return url.substring(i + 1);
        } else {
            return url;
        }

    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(File file);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}
