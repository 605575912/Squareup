package com.squareup.lib.utils;

import android.os.Handler;
import android.widget.Toast;

import com.squareup.lib.BaseApplication;

/**
 * Created by lzx on 2017/05/27 0027.
 */

public class ToastUtils {
    static Toast toast;

    public static void showToast(final CharSequence text) {
        if (text == null) {
            return;
        }
        Runnable toastRunnable = new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(BaseApplication.application, text, Toast.LENGTH_LONG);
                }
                toast.setText(text);
                toast.show();
            }
        };
        Handler handler = new Handler(BaseApplication.application.getMainLooper());
        handler.post(toastRunnable);
    }

    public static void showToast(final int resId) {
        try {
            CharSequence s = BaseApplication.application.getResources().getText(resId);
            showToast(s);
        } catch (Exception e) {
            showToast(String.valueOf(resId));
        }
    }
}
